package kz.btokmyrza.anygram.feature.chats.domain.model

sealed interface ChatMessageContentTypeEntity {

    data object Text : ChatMessageContentTypeEntity

    data object Animation : ChatMessageContentTypeEntity

    data object Audio : ChatMessageContentTypeEntity

    data object Document : ChatMessageContentTypeEntity

    data object PaidMedia : ChatMessageContentTypeEntity

    data object Photo : ChatMessageContentTypeEntity

    data object Sticker : ChatMessageContentTypeEntity

    data object Video : ChatMessageContentTypeEntity

    data object VideoNote : ChatMessageContentTypeEntity

    data object VoiceNote : ChatMessageContentTypeEntity

    data object ExpiredPhoto : ChatMessageContentTypeEntity

    data object ExpiredVideo : ChatMessageContentTypeEntity

    data object ExpiredVideoNote : ChatMessageContentTypeEntity

    data object ExpiredVoiceNote : ChatMessageContentTypeEntity

    data object Location : ChatMessageContentTypeEntity

    data object Venue : ChatMessageContentTypeEntity

    data object Contact : ChatMessageContentTypeEntity

    data object AnimatedEmoji : ChatMessageContentTypeEntity

    data object Dice : ChatMessageContentTypeEntity

    data object Game : ChatMessageContentTypeEntity

    data object Poll : ChatMessageContentTypeEntity

    data object Story : ChatMessageContentTypeEntity

    data object Invoice : ChatMessageContentTypeEntity

    data object Call : ChatMessageContentTypeEntity

    data object GroupCall : ChatMessageContentTypeEntity

    data object VideoChatScheduled : ChatMessageContentTypeEntity

    data object VideoChatStarted : ChatMessageContentTypeEntity

    data object VideoChatEnded : ChatMessageContentTypeEntity

    data object InviteVideoChatParticipants : ChatMessageContentTypeEntity

    data object BasicGroupChatCreate : ChatMessageContentTypeEntity

    data object SupergroupChatCreate : ChatMessageContentTypeEntity

    data object ChatChangeTitle : ChatMessageContentTypeEntity

    data object ChatChangePhoto : ChatMessageContentTypeEntity

    data object ChatDeletePhoto : ChatMessageContentTypeEntity

    data object ChatAddMembers : ChatMessageContentTypeEntity

    data object ChatJoinByLink : ChatMessageContentTypeEntity

    data object ChatJoinByRequest : ChatMessageContentTypeEntity

    data object ChatDeleteMember : ChatMessageContentTypeEntity

    data object ChatUpgradeTo : ChatMessageContentTypeEntity

    data object ChatUpgradeFrom : ChatMessageContentTypeEntity

    data object PinMessage : ChatMessageContentTypeEntity

    data object ScreenshotTaken : ChatMessageContentTypeEntity

    data object ChatSetBackground : ChatMessageContentTypeEntity

    data object ChatSetTheme : ChatMessageContentTypeEntity

    data object ChatSetMessageAutoDeleteTime : ChatMessageContentTypeEntity

    data object ChatBoost : ChatMessageContentTypeEntity

    data object ForumTopicCreated : ChatMessageContentTypeEntity

    data object ForumTopicEdited : ChatMessageContentTypeEntity

    data object ForumTopicIsClosedToggled : ChatMessageContentTypeEntity

    data object ForumTopicIsHiddenToggled : ChatMessageContentTypeEntity

    data object SuggestProfilePhoto : ChatMessageContentTypeEntity

    data object CustomServiceAction : ChatMessageContentTypeEntity

    data object GameScore : ChatMessageContentTypeEntity

    data object PaymentSuccessful : ChatMessageContentTypeEntity

    data object PaymentSuccessfulBot : ChatMessageContentTypeEntity

    data object PaymentRefunded : ChatMessageContentTypeEntity

    data object GiftedPremium : ChatMessageContentTypeEntity

    data object PremiumGiftCode : ChatMessageContentTypeEntity

    data object GiveawayCreated : ChatMessageContentTypeEntity

    data object Giveaway : ChatMessageContentTypeEntity

    data object GiveawayCompleted : ChatMessageContentTypeEntity

    data object GiveawayWinners : ChatMessageContentTypeEntity

    data object GiftedStars : ChatMessageContentTypeEntity

    data object GiveawayPrizeStars : ChatMessageContentTypeEntity

    data object Gift : ChatMessageContentTypeEntity

    data object UpgradedGift : ChatMessageContentTypeEntity

    data object RefundedUpgradedGift : ChatMessageContentTypeEntity

    data object PaidMessagesRefunded : ChatMessageContentTypeEntity

    data object PaidMessagePriceChanged : ChatMessageContentTypeEntity

    data object ContactRegistered : ChatMessageContentTypeEntity

    data object UsersShared : ChatMessageContentTypeEntity

    data object ChatShared : ChatMessageContentTypeEntity

    data object BotWriteAccessAllowed : ChatMessageContentTypeEntity

    data object WebAppDataSent : ChatMessageContentTypeEntity

    data object WebAppDataReceived : ChatMessageContentTypeEntity

    data object PassportDataSent : ChatMessageContentTypeEntity

    data object PassportDataReceived : ChatMessageContentTypeEntity

    data object ProximityAlertTriggered : ChatMessageContentTypeEntity

    data object Unsupported : ChatMessageContentTypeEntity
}
