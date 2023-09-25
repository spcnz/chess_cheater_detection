//package yauza.chess
//
//import gaia.streams.common.testing.TopologyTest
//import io.circe._
//import io.circe.parser._
//import org.apache.kafka.streams.scala.ImplicitConversions._
//import org.apache.kafka.streams.scala.serialization.Serdes._
//import org.apache.kafka.streams.{TestInputTopic, TestOutputTopic, Topology}
//import org.scalatest.GivenWhenThen
//import yauza.chess.fixture.AccountUpdateEventFixture
//
//import java.time.Instant
//import java.util.Properties
//import scala.reflect.io.Path
//
//class OracleAtlasAccountTopologyTest
//    extends TopologyTest
//    with Serializers
//    with AccountUpdateEventFixture
//    with GivenWhenThen {
//
//  override def schemaRegistryScope: String = classOf[OracleAtlasAccountTopologyTest].toString
//
//  override def properties: Properties = config.streamProperties
//
//  override def stateDir: Option[Path] = Some(config.stateDir / config.applicationId)
//
//  override def topology: Topology = TopologyBuilder().build
//
//  var atlasAccountUpdateEventTopic: TestInputTopic[String, AccountUpdatedEvent] = _
//  var customerOutputTopic: TestOutputTopic[String, String] = _
//  var addressOutputTopic: TestOutputTopic[String, String] = _
//  var limitOutputTopic: TestOutputTopic[String, String] = _
//
//  behavior of "Oracle Atlas Account topology"
//  it should "ignore DELETE account updates" in {
//    When("account is deleted")
//    publishAccountUpdateEvent(
//      eventType = UpdateEventType.DELETED,
//      accountType = AccountType.REAL
//    )
//
//    Then("update message is ignored")
//    a[NoSuchElementException] shouldBe thrownBy(customerOutputTopic.readValue)
//    a[NoSuchElementException] shouldBe thrownBy(addressOutputTopic.readValue)
//    a[NoSuchElementException] shouldBe thrownBy(limitOutputTopic.readValue)
//  }
//  it should "ignore updates for SHADOW accounts" in {
//    When("account is shadow")
//    publishAccountUpdateEvent(
//      eventType = UpdateEventType.UPDATED,
//      accountType = AccountType.SHADOW
//    )
//
//    Then("update message is ignored")
//    a[NoSuchElementException] shouldBe thrownBy(customerOutputTopic.readValue)
//    a[NoSuchElementException] shouldBe thrownBy(addressOutputTopic.readValue)
//    a[NoSuchElementException] shouldBe thrownBy(limitOutputTopic.readValue)
//  }
//  it should "ignore account updates made by Oracle user" in {
//    When("account update is made by Oracle user")
//    publishAccountUpdateEvent(
//      eventType = UpdateEventType.UPDATED,
//      accountType = AccountType.REAL,
//      modifiedBy = config.oracleAtlasAccount.filter.oracleUser
//    )
//
//    Then("update message is ignored")
//    a[NoSuchElementException] shouldBe thrownBy(customerOutputTopic.readValue)
//    a[NoSuchElementException] shouldBe thrownBy(addressOutputTopic.readValue)
//    a[NoSuchElementException] shouldBe thrownBy(limitOutputTopic.readValue)
//  }
//  it should "ignore account updates without account ID" in {
//    When("account update without account_id is published")
//    publishAccountUpdateEvent(
//      eventType = UpdateEventType.UPDATED,
//      accountId = None,
//      accountType = AccountType.REAL
//    )
//
//    Then("update message is ignored")
//    a[NoSuchElementException] shouldBe thrownBy(customerOutputTopic.readValue)
//    a[NoSuchElementException] shouldBe thrownBy(addressOutputTopic.readValue)
//    a[NoSuchElementException] shouldBe thrownBy(limitOutputTopic.readValue)
//  }
//  it should "transform accounts to customer, address and limit messages" in {
//    Given("there is no matching Empirebet player")
//
//    When("Atlas account update is published")
//
//    publishAccountUpdateEvent(
//      eventType = UpdateEventType.UPDATED,
//      accountId = Some(1L),
//      accountType = AccountType.REAL
//    )
//
//    Then("transformed customer, address and limit messages are produced")
//
//    val customer = customerOutputTopic.readRecord()
//    val address = addressOutputTopic.readRecord()
//    val limit = limitOutputTopic.readRecord()
//
//    customer.key shouldEqual "1"
//    address.key shouldEqual "1"
//    limit.key shouldEqual "1"
//
//    val customerJson = parse(customer.value()).getOrElse(Json.Null)
//    val addressJson = parse(address.value()).getOrElse(Json.Null)
//    val limitJson = parse(limit.value()).getOrElse(Json.Null)
//
//    customerJson shouldNot be(Json.Null)
//    addressJson shouldNot be(Json.Null)
//    limitJson shouldNot be(Json.Null)
//
//    customerJson.hcursor.get[String]("Account_ID").toOption should contain("1")
//    customerJson.hcursor.get[String]("Source_ID").toOption should contain("AWS")
//    customerJson.hcursor.get[Long]("Source_of_Registration_(aff_ID)").toOption should contain(6789)
//    customerJson.hcursor.get[String]("First_Name").toOption should contain("John")
//    customerJson.hcursor.get[String]("Last_Name").toOption should contain("Doe")
//    customerJson.hcursor.get[String]("Username").toOption should contain("john_doe")
//    customerJson.hcursor.get[String]("Skrill_Username").toOption should be(None)
//    customerJson.hcursor.get[String]("Gender").toOption should contain("MALE")
//    customerJson.hcursor.get[String]("Prefix").toOption should be(None)
//    customerJson.hcursor.get[String]("Birthday").toOption should contain("1990-5-15")
//    customerJson.hcursor.get[String]("Activation_Date").toOption should contain(
//      "2023-08-30 11:00:00"
//    )
//    customerJson.hcursor.get[String]("Registration_Date").toOption should contain(
//      "2023-08-29 10:00:00"
//    )
//    customerJson.hcursor.get[String]("Created_Date").toOption should be(None)
//    customerJson.hcursor.get[String]("E-Mail_Address").toOption should contain("john@example.com")
//    customerJson.hcursor.get[Boolean]("E-Mail_Address_Valid").toOption should contain(true)
//    customerJson.hcursor.get[String]("Phone_Number").toOption should contain("1234567890")
//    customerJson.hcursor.get[Boolean]("Phone_Number_Valid").toOption should contain(true)
//    customerJson.hcursor.get[Boolean]("Player_Verified").toOption should contain(true)
//    customerJson.hcursor.get[String]("Player_Status").toOption should contain("Active")
//    customerJson.hcursor.get[Boolean]("Registration_Status").toOption should contain(true)
//    customerJson.hcursor.get[String]("Language").toOption should contain("en")
//    customerJson.hcursor.get[String]("Primary_Language").toOption should contain("en")
//    customerJson.hcursor.get[String]("App_Downloaded").toOption should be(None)
//    customerJson.hcursor.get[Boolean]("To_Be_Deleted").toOption should contain(false)
//    customerJson.hcursor.get[Boolean]("Double_Opt_In").toOption should contain(false)
//    customerJson.hcursor.get[Boolean]("Player_Self_Deactivated").toOption should contain(false)
//    customerJson.hcursor.get[String]("Player_Currency").toOption should contain("EUR")
//    customerJson.hcursor.get[Boolean]("Okay_To_E-Mail").toOption should contain(true)
//    customerJson.hcursor.get[Boolean]("Okay_To_SMS").toOption should contain(false)
//    customerJson.hcursor.get[Boolean]("Okay_To_Push").toOption should contain(true)
//    customerJson.hcursor.get[String]("Registration_Platform").toOption should contain("ANDROID")
//
//    addressJson.hcursor.get[String]("Account_ID").toOption should contain("1")
//    addressJson.hcursor.get[String]("Source_ID").toOption should contain("AWS")
//    addressJson.hcursor.get[String]("Address_Line_1").toOption should contain("123 Main St")
//    addressJson.hcursor.get[String]("Address_Line_2").toOption should be(None)
//    addressJson.hcursor.get[Int]("Apartment_Number").toOption should be(None)
//    addressJson.hcursor.get[String]("City").toOption should contain("City")
//    addressJson.hcursor.get[String]("Country").toOption should contain("Country")
//    addressJson.hcursor.get[String]("State").toOption should contain("Country")
//    addressJson.hcursor.get[String]("Zip_Code").toOption should contain("12345")
//    addressJson.hcursor.get[String]("Postal_Code").toOption should contain("12345")
//
//    limitJson.hcursor.get[String]("Account_ID").toOption should contain("1")
//    limitJson.hcursor.get[String]("Source_ID").toOption should contain("AWS")
//    limitJson.hcursor.get[Boolean]("Player_Limit_Status").toOption should contain(false)
//    limitJson.hcursor.get[Seq[String]]("Player_Limit_Type").toOption should contain(Seq.empty)
//    limitJson.hcursor
//      .get[String]("Player_Limit_Deposit_Start_Date")
//      .toOption should be(None)
//    limitJson.hcursor.get[String]("Player_Limit_Deposit_End_Date").toOption should be(
//      None
//    )
//    limitJson.hcursor.get[String]("Player_Limit_Loss_Start_Date").toOption should be(
//      None
//    )
//    limitJson.hcursor.get[String]("Player_Limit_Loss_End_Date").toOption should be(
//      None
//    )
//    limitJson.hcursor.get[String]("Player_Limit_Stake_Start_Date").toOption should be(
//      None
//    )
//    limitJson.hcursor.get[String]("Player_Limit_Stake_End_Date").toOption should be(
//      None
//    )
//    limitJson.hcursor.get[Boolean]("Self_Exclusion_Status").toOption should contain(false)
//    limitJson.hcursor.get[String]("Self_Exclusion_Start_Date").toOption should contain(
//      "2023-09-01 10:00:00"
//    )
//    limitJson.hcursor.get[String]("Self_Exclusion_End_Date").toOption should be(None)
//  }
//
//  def publishAccountUpdateEvent(
//      eventType: UpdateEventType,
//      accountId: Option[Long] = Some(1L),
//      accountType: AccountType,
//      modifiedBy: Long = 100L
//  ): Unit = {
//    val accountEvent = accountUpdateEvent(
//      eventType = eventType,
//      accountId = accountId,
//      accountType = accountType,
//      modifiedBy = Some(modifiedBy),
//      gender = Some(Gender.MALE),
//      birthdate = Some("1990-5-15"),
//      nickname = Some("john_doe"),
//      firstName = Some("John"),
//      lastName = Some("Doe"),
//      phone = Some("1234567890"),
//      email = Some("john@example.com"),
//      street = Some("123 Main St"),
//      city = Some("City"),
//      postalCode = Some("12345"),
//      country = Some("Country"),
//      active = Some(true),
//      blocked = Some(false),
//      hidden = Some(false),
//      createTime = Some(Instant.parse("2023-08-29T10:00:00.00Z").toEpochMilli),
//      createdBy = Some(1),
//      currencies = List(MarketCurrency("EUR", 948)),
//      verified = None,
//      activationTime = Some(Instant.parse("2023-08-30T11:00:00.00Z").toEpochMilli),
//      affiliateId = Some("6789"),
//      notificationLanguage = Some("en"),
//      newsletterEmail = Some(true),
//      newsletterSms = Some(false),
//      newsletterPush = Some(true),
//      exclusionConfiguration = Some(
//        AccountExclusionConfiguration(
//          exclusionStartTime = Some(Instant.parse("2023-09-01T10:00:00.00Z").toEpochMilli),
//          exclusionDuration = Some(ExclusionDuration.ONE_DAY),
//          exclusionType = Some(ExclusionType.SELF_EXCLUSION)
//        )
//      ),
//      registrationPlatform = Some("ANDROID")
//    )
//    atlasAccountUpdateEventTopic.pipeInput(accountId.map(_.toString).getOrElse(""), accountEvent)
//  }
//
//  override def createInputTopics(): Unit =
//    atlasAccountUpdateEventTopic = driver.createInputTopic(
//      config.oracleAtlasAccount.topic.atlas.account,
//      stringSerde.serializer(),
//      atlasAccountSerde.serializer()
//    )
//
//  override def createOutputTopics(): Unit = {
//    customerOutputTopic = driver.createOutputTopic(
//      config.oracleAtlasAccount.topic.skyline.customer,
//      stringSerde.deserializer(),
//      stringSerde.deserializer()
//    )
//    addressOutputTopic = driver.createOutputTopic(
//      config.oracleAtlasAccount.topic.skyline.address,
//      stringSerde.deserializer(),
//      stringSerde.deserializer()
//    )
//    limitOutputTopic = driver.createOutputTopic(
//      config.oracleAtlasAccount.topic.skyline.limit,
//      stringSerde.deserializer(),
//      stringSerde.deserializer()
//    )
//  }
//
//}
