
import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.google.firebase.messaging.RemoteMessage
import com.sendbird.android.SendBird
import com.sendbird.android.SendBird.PushTokenRegistrationStatus
import com.sendbird.android.SendBirdException
import com.sendbird.android.SendBirdPushHandler
import com.sendbird.android.SendBirdPushHelper
import com.sendbird.android.constant.StringSet
import org.json.JSONException
import org.json.JSONObject
import java.util.concurrent.atomic.AtomicReference


class MyFirebaseMessagingService : SendBirdPushHandler() {
    interface ITokenResult {
        fun onPushTokenReceived(pushToken: String?, e: SendBirdException?)
    }

    override fun onNewToken(token: String) {
        pushToken.set(token)

        // Register a registration token to Sendbird server.
        SendBird.registerPushTokenForCurrentUser(
            StringSet.token
        ) { ptrs, e ->
            if (e != null) {
                Log.d(e.message, e.message.toString())
                // Handle error.
            }
            if (ptrs == PushTokenRegistrationStatus.PENDING) {
                // A token registration is pending.
                // Retry the registration after a connection has been successfully established.
            }
        }
    }

    // Invoked when a notification message has been delivered to the current user's client app.
    override fun onMessageReceived(context: Context?, remoteMessage: RemoteMessage) {
        var channelUrl: String? = null
        try {
            if (remoteMessage.data.containsKey("sendbird")) {
                val sendbird = JSONObject(remoteMessage.data["sendbird"])
                val channel = sendbird["channel"] as JSONObject
                channelUrl = channel["channel_url"] as String
                // If you want to customize a notification with the received FCM message,
                // write your method like the sendNotification() below.
                sendNotification(
                    context,
                    remoteMessage.data["push_title"], remoteMessage.data["message"], channelUrl
                )
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    //protected val isUniquePushToken: Boolean
//        get() = false

    // alwaysReceiveMessage() determines whether push notifications for new messages
    // will be delivered even when the app is in foreground.
    // The default is false, meaning push notifications will be delivered
    // only when the app is in the background.
    override fun alwaysReceiveMessage(): Boolean {
        return false
    }

    companion object {
        private const val TAG = "MyFirebaseMsgService"
        private val pushToken = AtomicReference<String>()
        fun sendNotification(
            context: Context?,
            messageTitle: String?,
            messageBody: String?,
            channelUrl: String?
        ) {
            // Customize your notification containing the received FCM message.
        }

        fun getPushToken(listener: ITokenResult?) {
            val token = pushToken.get()
            if (!TextUtils.isEmpty(token)) {
                listener!!.onPushTokenReceived(token, null)
                return
            }
            SendBirdPushHelper.getPushToken { newToken, e ->
                listener?.onPushTokenReceived(newToken, e)
                if (e == null) {
                    pushToken.set(newToken)
                }
            }
        }
    }
}