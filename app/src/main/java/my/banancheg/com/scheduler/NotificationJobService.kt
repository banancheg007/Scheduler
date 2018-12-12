package my.banancheg.com.scheduler

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v4.app.NotificationCompat


class NotificationJobService : JobService() {
    lateinit internal var mNotifyManager: NotificationManager

    override fun onStartJob(jobParameters: JobParameters): Boolean {

        createNotificationChannel()

        val contentPendingIntent = PendingIntent.getActivity(
            this, 0, Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
            .setContentTitle(getString(R.string.job_service))
            .setContentText(getString(R.string.job_running))
            .setContentIntent(contentPendingIntent)
            .setSmallIcon(R.drawable.ic_job_running)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setAutoCancel(true)

        mNotifyManager.notify(0, builder.build())
        return false
    }

    override fun onStopJob(jobParameters: JobParameters): Boolean {
        return false
    }


    fun createNotificationChannel() {

        mNotifyManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            val notificationChannel = NotificationChannel(
                PRIMARY_CHANNEL_ID,
                getString(R.string.job_service_notification),
                NotificationManager.IMPORTANCE_HIGH
            )

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = getString(R.string.notification_channel_description)

            mNotifyManager.createNotificationChannel(notificationChannel)
        }
    }

    companion object {

        private val PRIMARY_CHANNEL_ID = "primary_notification_channel"
    }
}
