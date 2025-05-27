package kz.btokmyrza.anygram.feature.chats.data.mapper

import kz.btokmyrza.anygram.feature.chats.domain.model.ChatMessageContentTypeEntity
import org.drinkless.tdlib.TdApi

class ChatMessageContentMapper {

    fun toChatMessageContentTypeEntity(
        content: TdApi.MessageContent?,
    ): ChatMessageContentTypeEntity =
        when (content) {
            is TdApi.MessageText -> {
                ChatMessageContentTypeEntity.Text
            }
            is TdApi.MessageAnimation -> {
                ChatMessageContentTypeEntity.Animation
            }
            is TdApi.MessageAudio -> {
                ChatMessageContentTypeEntity.Audio
            }
            is TdApi.MessageDocument -> {
                ChatMessageContentTypeEntity.Document
            }
            is TdApi.MessagePaidMedia -> {
                ChatMessageContentTypeEntity.PaidMedia
            }
            is TdApi.MessagePhoto -> {
                ChatMessageContentTypeEntity.Photo
            }
            is TdApi.MessageSticker -> {
                ChatMessageContentTypeEntity.Sticker
            }
            is TdApi.MessageVideo -> {
                ChatMessageContentTypeEntity.Video
            }
            is TdApi.MessageVideoNote -> {
                ChatMessageContentTypeEntity.VideoNote
            }
            is TdApi.MessageVoiceNote -> {
                ChatMessageContentTypeEntity.VoiceNote
            }
            is TdApi.MessageExpiredPhoto -> {
                ChatMessageContentTypeEntity.ExpiredPhoto
            }
            is TdApi.MessageExpiredVideo -> {
                ChatMessageContentTypeEntity.ExpiredVideo
            }
            is TdApi.MessageExpiredVideoNote -> {
                ChatMessageContentTypeEntity.ExpiredVideoNote
            }
            is TdApi.MessageExpiredVoiceNote -> {
                ChatMessageContentTypeEntity.ExpiredVoiceNote
            }
            is TdApi.MessageLocation -> {
                ChatMessageContentTypeEntity.Location
            }
            is TdApi.MessageVenue -> {
                ChatMessageContentTypeEntity.Venue
            }
            is TdApi.MessageContact -> {
                ChatMessageContentTypeEntity.Contact
            }
            is TdApi.MessageAnimatedEmoji -> {
                ChatMessageContentTypeEntity.AnimatedEmoji
            }
            is TdApi.MessageDice -> {
                ChatMessageContentTypeEntity.Dice
            }
            is TdApi.MessageGame -> {
                ChatMessageContentTypeEntity.Game
            }
            is TdApi.MessagePoll -> {
                ChatMessageContentTypeEntity.Poll
            }
            is TdApi.MessageStory -> {
                ChatMessageContentTypeEntity.Story
            }
            is TdApi.MessageInvoice -> {
                ChatMessageContentTypeEntity.Invoice
            }
            is TdApi.MessageCall -> {
                ChatMessageContentTypeEntity.Call
            }
            is TdApi.MessageGroupCall -> {
                ChatMessageContentTypeEntity.GroupCall
            }
            is TdApi.MessageVideoChatScheduled -> {
                ChatMessageContentTypeEntity.VideoChatScheduled
            }
            is TdApi.MessageVideoChatStarted -> {
                ChatMessageContentTypeEntity.VideoChatStarted
            }
            is TdApi.MessageVideoChatEnded -> {
                ChatMessageContentTypeEntity.VideoChatEnded
            }
            is TdApi.MessageInviteVideoChatParticipants -> {
                ChatMessageContentTypeEntity.InviteVideoChatParticipants
            }
            is TdApi.MessageBasicGroupChatCreate -> {
                ChatMessageContentTypeEntity.BasicGroupChatCreate
            }
            is TdApi.MessageSupergroupChatCreate -> {
                ChatMessageContentTypeEntity.SupergroupChatCreate
            }
            is TdApi.MessageChatChangeTitle -> {
                ChatMessageContentTypeEntity.ChatChangeTitle
            }
            is TdApi.MessageChatChangePhoto -> {
                ChatMessageContentTypeEntity.ChatChangePhoto
            }
            is TdApi.MessageChatDeletePhoto -> {
                ChatMessageContentTypeEntity.ChatDeletePhoto
            }
            is TdApi.MessageChatAddMembers -> {
                ChatMessageContentTypeEntity.ChatAddMembers
            }
            is TdApi.MessageChatJoinByLink -> {
                ChatMessageContentTypeEntity.ChatJoinByLink
            }
            is TdApi.MessageChatJoinByRequest -> {
                ChatMessageContentTypeEntity.ChatJoinByRequest
            }
            is TdApi.MessageChatDeleteMember -> {
                ChatMessageContentTypeEntity.ChatDeleteMember
            }
            is TdApi.MessageChatUpgradeTo -> {
                ChatMessageContentTypeEntity.ChatUpgradeTo
            }
            is TdApi.MessageChatUpgradeFrom -> {
                ChatMessageContentTypeEntity.ChatUpgradeFrom
            }
            is TdApi.MessagePinMessage -> {
                ChatMessageContentTypeEntity.PinMessage
            }
            is TdApi.MessageScreenshotTaken -> {
                ChatMessageContentTypeEntity.ScreenshotTaken
            }
            is TdApi.MessageChatSetBackground -> {
                ChatMessageContentTypeEntity.ChatSetBackground
            }
            is TdApi.MessageChatSetTheme -> {
                ChatMessageContentTypeEntity.ChatSetTheme
            }
            is TdApi.MessageChatSetMessageAutoDeleteTime -> {
                ChatMessageContentTypeEntity.ChatSetMessageAutoDeleteTime
            }
            is TdApi.MessageChatBoost -> {
                ChatMessageContentTypeEntity.ChatBoost
            }
            is TdApi.MessageForumTopicCreated -> {
                ChatMessageContentTypeEntity.ForumTopicCreated
            }
            is TdApi.MessageForumTopicEdited -> {
                ChatMessageContentTypeEntity.ForumTopicEdited
            }
            is TdApi.MessageForumTopicIsClosedToggled -> {
                ChatMessageContentTypeEntity.ForumTopicIsClosedToggled
            }
            is TdApi.MessageForumTopicIsHiddenToggled -> {
                ChatMessageContentTypeEntity.ForumTopicIsHiddenToggled
            }
            is TdApi.MessageSuggestProfilePhoto -> {
                ChatMessageContentTypeEntity.SuggestProfilePhoto
            }
            is TdApi.MessageCustomServiceAction -> {
                ChatMessageContentTypeEntity.CustomServiceAction
            }
            is TdApi.MessageGameScore -> {
                ChatMessageContentTypeEntity.GameScore
            }
            is TdApi.MessagePaymentSuccessful -> {
                ChatMessageContentTypeEntity.PaymentSuccessful
            }
            is TdApi.MessagePaymentSuccessfulBot -> {
                ChatMessageContentTypeEntity.PaymentSuccessfulBot
            }
            is TdApi.MessagePaymentRefunded -> {
                ChatMessageContentTypeEntity.PaymentRefunded
            }
            is TdApi.MessageGiftedPremium -> {
                ChatMessageContentTypeEntity.GiftedPremium
            }
            is TdApi.MessagePremiumGiftCode -> {
                ChatMessageContentTypeEntity.PremiumGiftCode
            }
            is TdApi.MessageGiveawayCreated -> {
                ChatMessageContentTypeEntity.GiveawayCreated
            }
            is TdApi.MessageGiveaway -> {
                ChatMessageContentTypeEntity.Giveaway
            }
            is TdApi.MessageGiveawayCompleted -> {
                ChatMessageContentTypeEntity.GiveawayCompleted
            }
            is TdApi.MessageGiveawayWinners -> {
                ChatMessageContentTypeEntity.GiveawayWinners
            }
            is TdApi.MessageGiftedStars -> {
                ChatMessageContentTypeEntity.GiftedStars
            }
            is TdApi.MessageGiveawayPrizeStars -> {
                ChatMessageContentTypeEntity.GiveawayPrizeStars
            }
            is TdApi.MessageGift -> {
                ChatMessageContentTypeEntity.Gift
            }
            is TdApi.MessageUpgradedGift -> {
                ChatMessageContentTypeEntity.UpgradedGift
            }
            is TdApi.MessageRefundedUpgradedGift -> {
                ChatMessageContentTypeEntity.RefundedUpgradedGift
            }
            is TdApi.MessagePaidMessagesRefunded -> {
                ChatMessageContentTypeEntity.PaidMessagesRefunded
            }
            is TdApi.MessagePaidMessagePriceChanged -> {
                ChatMessageContentTypeEntity.PaidMessagePriceChanged
            }
            is TdApi.MessageContactRegistered -> {
                ChatMessageContentTypeEntity.ContactRegistered
            }
            is TdApi.MessageUsersShared -> {
                ChatMessageContentTypeEntity.UsersShared
            }
            is TdApi.MessageChatShared -> {
                ChatMessageContentTypeEntity.ChatShared
            }
            is TdApi.MessageBotWriteAccessAllowed -> {
                ChatMessageContentTypeEntity.BotWriteAccessAllowed
            }
            is TdApi.MessageWebAppDataSent -> {
                ChatMessageContentTypeEntity.WebAppDataSent
            }
            is TdApi.MessageWebAppDataReceived -> {
                ChatMessageContentTypeEntity.WebAppDataReceived
            }
            is TdApi.MessagePassportDataSent -> {
                ChatMessageContentTypeEntity.PassportDataSent
            }
            is TdApi.MessagePassportDataReceived -> {
                ChatMessageContentTypeEntity.PassportDataReceived
            }
            is TdApi.MessageProximityAlertTriggered -> {
                ChatMessageContentTypeEntity.ProximityAlertTriggered
            }
            else -> {
                ChatMessageContentTypeEntity.Unsupported
            }
        }
}
