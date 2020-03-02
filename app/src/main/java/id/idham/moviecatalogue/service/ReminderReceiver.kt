package id.idham.moviecatalogue.service

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import id.idham.moviecatalogue.R
import id.idham.moviecatalogue.data.DataManager
import id.idham.moviecatalogue.data.network.response.MovieModel
import id.idham.moviecatalogue.ui.main.MainActivity
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ReminderReceiver : BroadcastReceiver(), KoinComponent {

    private val dataManager by inject<DataManager>()
    private var dataRelease: ArrayList<MovieModel> = ArrayList()

    companion object {
        const val TYPE_DAILY = "DailyReminder"
        const val TYPE_RELEASE = "ReleaseReminder"
        const val EXTRA_TYPE = "type"

        private const val ID_DAILY = 100
        private const val ID_RELEASE = 101
        private const val GROUP_KEY_RELEASE = "group_key_release"
        private const val NOTIFICATION_REQUEST_CODE = 200
    }

    override fun onReceive(context: Context, intent: Intent) {
        val type = intent.getStringExtra(EXTRA_TYPE)
        val notifId = if (type.equals(TYPE_DAILY, ignoreCase = true)) ID_DAILY else ID_RELEASE

        if (notifId == ID_DAILY) {
            showAlarmNotification(
                context,
                context.getString(R.string.app_name),
                context.getString(R.string.alarm_daily),
                notifId
            )
        } else {
            dataRelease.clear()
            val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            dateFormatter.isLenient = false
            val todayDate: String = dateFormatter.format(Date())

            dataManager.getMoviesByDate(todayDate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<List<MovieModel>> {
                    override fun onSuccess(t: List<MovieModel>) {
                        if (t.isNotEmpty()) {
                            for (item in t) dataRelease.add(item)
                            showAlarmNotification(
                                context, context.getString(R.string.alarm_released), "", notifId
                            )
                        }
                    }

                    override fun onSubscribe(d: Disposable) {}
                    override fun onError(e: Throwable) {}
                })
        }
    }

    private fun showAlarmNotification(
        context: Context, title: String, message: String, notifId: Int
    ) {
        val channelId = "Channel_1"
        val channelName = "AlarmManager channel"

        val notificationManagerCompat =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent = PendingIntent.getActivity(
            context, NOTIFICATION_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder: NotificationCompat.Builder

        if (notifId == ID_DAILY) {
            builder = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_local_movies_black_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
                .setSound(alarmSound)
                .setAutoCancel(true)
        } else {
            val inboxStyle = NotificationCompat.InboxStyle()
                .setBigContentTitle(title)
            for (item in dataRelease) {
                inboxStyle.addLine(item.title)
            }
            builder = NotificationCompat.Builder(context, channelId)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_local_movies_black_24dp)
                .setGroup(GROUP_KEY_RELEASE)
                .setGroupSummary(true)
                .setContentIntent(pendingIntent)
                .setStyle(inboxStyle)
                .setAutoCancel(true)
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
                .setSound(alarmSound)
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT
            )

            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)

            builder.setChannelId(channelId)
            notificationManagerCompat.createNotificationChannel(channel)
        }

        val notification = builder.build()
        notificationManagerCompat.notify(notifId, notification)
    }

    fun setDailyAlarm(context: Context, type: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReminderReceiver::class.java)
        intent.putExtra(EXTRA_TYPE, type)

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 7)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        val pendingIntent = PendingIntent.getBroadcast(context, ID_DAILY, intent, 0)
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent
        )
    }

    fun setReleaseAlarm(context: Context, type: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReminderReceiver::class.java)
        intent.putExtra(EXTRA_TYPE, type)

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 8)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        val pendingIntent = PendingIntent.getBroadcast(context, ID_RELEASE, intent, 0)
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent
        )
    }

    fun cancelAlarm(context: Context, type: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReminderReceiver::class.java)
        val requestCode = if (type.equals(TYPE_DAILY, ignoreCase = true)) ID_DAILY else ID_RELEASE
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)
        pendingIntent.cancel()

        alarmManager.cancel(pendingIntent)
    }

    fun isAlarmSet(context: Context, type: String): Boolean {
        val intent = Intent(context, ReminderReceiver::class.java)
        val requestCode = if (type.equals(TYPE_DAILY, ignoreCase = true)) ID_DAILY else ID_RELEASE

        return PendingIntent.getBroadcast(
            context, requestCode, intent, PendingIntent.FLAG_NO_CREATE
        ) != null
    }

}
