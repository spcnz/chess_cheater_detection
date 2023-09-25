//package yauza.chess.fixture
//
//import atlas.avro.message.v1.commons.UpdateEventType
//import atlas.avro.message.v1.territory.hierarchy.market.MarketCurrency
//import atlas.avro.message.v2.account.{
//  Account,
//  AccountExclusionConfiguration,
//  AccountLimitConfiguration,
//  AccountPaymentInfo,
//  AccountType,
//  AccountUpdatedEvent,
//  Gender
//}
//
//trait AccountUpdateEventFixture {
//
//  def accountUpdateEvent(
//      eventType: UpdateEventType = UpdateEventType.UPDATED,
//      accountId: Option[Long],
//      accountType: AccountType,
//      gender: Option[Gender] = None,
//      birthdate: Option[String] = None,
//      nickname: Option[String] = None,
//      firstName: Option[String] = None,
//      lastName: Option[String] = None,
//      phone: Option[String] = None,
//      email: Option[String] = None,
//      street: Option[String] = None,
//      city: Option[String] = None,
//      postalCode: Option[String] = None,
//      country: Option[String] = None,
//      active: Option[Boolean] = Some(false),
//      blocked: Option[Boolean] = Some(false),
//      hidden: Option[Boolean] = Some(false),
//      createTime: Option[Long] = None,
//      createdBy: Option[Long] = None,
//      modifiedBy: Option[Long] = None,
//      currencies: Seq[MarketCurrency] = Seq.empty,
//      verified: Option[Boolean] = Some(false),
//      activationTime: Option[Long] = None,
//      affiliateId: Option[String] = None,
//      notificationLanguage: Option[String] = None,
//      newsletterEmail: Option[Boolean] = Some(false),
//      newsletterSms: Option[Boolean] = Some(false),
//      newsletterPush: Option[Boolean] = Some(false),
//      limitConfigurations: Option[Seq[AccountLimitConfiguration]] = None,
//      exclusionConfiguration: Option[AccountExclusionConfiguration] = None,
//      registrationPlatform: Option[String] = None,
//      paymentInfo: Seq[AccountPaymentInfo] = Seq.empty
//  ): AccountUpdatedEvent =
//    AccountUpdatedEvent(
//      `type` = eventType,
//      account = Account(
//        accountId = accountId,
//        accountType = accountType,
//        gender = gender,
//        birthdate = birthdate,
//        nickname = nickname,
//        firstName = firstName,
//        lastName = lastName,
//        phone = phone,
//        email = email,
//        street = street,
//        city = city,
//        postalCode = postalCode,
//        country = country,
//        active = active,
//        blocked = blocked,
//        hidden = hidden,
//        createTime = createTime,
//        createdBy = createdBy,
//        modifiedBy = modifiedBy,
//        currencies = currencies,
//        verified = verified,
//        activationTime = activationTime,
//        affiliateId = affiliateId,
//        notificationLanguage = notificationLanguage,
//        newsletterEmail = newsletterEmail,
//        newsletterSms = newsletterSms,
//        newsletterPush = newsletterPush,
//        limitConfigurations = limitConfigurations,
//        exclusionConfiguration = exclusionConfiguration,
//        registrationPlatform = registrationPlatform,
//        paymentInfo = paymentInfo
//      )
//    )
//}
