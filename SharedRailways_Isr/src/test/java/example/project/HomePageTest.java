package example.project;

import example.project.pages.DrivePlanChooseTrainPage;
import example.project.pages.DrivePlanPricePage;
import example.project.pages.HomePage;
import example.project.pages.SearchTrainsExceptionPage;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.selenium.factory.WebDriverPool;

//import java.time.LocalDate;

/**
 * Created by Inka on 28-Apr-17.
 */
public class HomePageTest extends TestNgTestBase {
    private static Logger Log = Logger.getLogger(LogLog4j.class.getName());
    private static final String URL_LINK =
            "http://www1.rail.co.il/EN/Pages/Homepage.aspx";


    private HomePage homePage;
    private DrivePlanChooseTrainPage drivePlanChooseTrainPage;
    private SearchTrainsExceptionPage searchTrainsExceptionPage;
    private DrivePlanPricePage drivePlanPricePage;
   // LocalDate currentDate = LocalDate.now();


     //---------------

    @BeforeMethod(alwaysRun = true)
    public void initPageObjects() {
        homePage = PageFactory.initElements(driver, HomePage.class);
        drivePlanChooseTrainPage = PageFactory.initElements(driver, DrivePlanChooseTrainPage.class);
        searchTrainsExceptionPage = PageFactory.initElements(driver, SearchTrainsExceptionPage.class);
        drivePlanPricePage = PageFactory.initElements(driver,DrivePlanPricePage.class);
        driver.get(URL_LINK);
        homePage.waitForHomePageIsLoadedTime50();

    }



    @BeforeClass(alwaysRun = true)
    public void initWebDriver() {
        driver = WebDriverPool.DEFAULT.getDriver(gridHubUrl, capabilities);
    }

    @Test(groups = {"smoke", "regression"})
    //@Test(dataProviderClass = DataProviders.class, dataProvider = "negativeAdmin")
    public void positiveApplicationLoadingTest() {
        Log.info("----Test: positiveApplicationLoadingTest");
        Assert.assertTrue(homePage.isOnHomePage(),"Not passed. User is not on Home page");
    }

    @Test(groups = {"smoke", "regression"},dataProviderClass = DataProviders.class, dataProvider = "positiveSearch")
    public void positiveSearchTrainsOneWayTicket(String originStation, String destinationStation, String checkBox, String numberDays, String time) {
        int plusDays = Integer.parseInt(numberDays);
        System.out.println(plusDays+1);
        Log.info("----Test: positiveSearchTrainsOneWayTicket: "+originStation+", "+destinationStation+", "+checkBox+", "+plusDays+", "+time);

        homePage.choseOriginStation(originStation)
                .choseDestinationStation(destinationStation)
                .fillDateField(plusDays)
                .clickDepartureOrArrivalCheckBox(checkBox)
                .choseDepartureTime(time)
                .clickOnSearchButton();
        drivePlanChooseTrainPage.waitForDrivePlanChooseTrainIsLoadedTime50();

        Assert.assertTrue(drivePlanChooseTrainPage.isOnDrivePlanChooseTrain()
                            &&drivePlanChooseTrainPage.isOneWayTicket()
                            &&drivePlanChooseTrainPage.isCorrectOriginStation(originStation)
                            &&drivePlanChooseTrainPage.isCorrectDestinationStation(destinationStation)
                            &&drivePlanChooseTrainPage.isCorrectOneWayDate(plusDays),"Not passed. Some verifications are not passed.");
    }

    @Test(groups = "regression")
    public void negativeSearchTrainsOneWayTicket01() {
        Log.info("----Test: negativeSearchTrainsOneWayTicket01 - origin and destination are empty ");
        homePage.clickOnSearchButton();
        searchTrainsExceptionPage.waitForSearchTrainsExceptionIsLoadedTime50();
        Assert.assertTrue(searchTrainsExceptionPage.isOnSearchTrainsExceptionPage()
                        &&searchTrainsExceptionPage.isErrorSummaryCorrect(2)
                        &&searchTrainsExceptionPage.isErrorN1Correct("origin")
                        &&searchTrainsExceptionPage.isErrorN2Correct("destination"), "Not passed. Some verificatios are not passed.");

    }

    @Test(groups = "regression")
    public void negativeSearchTrainsOneWayTicket02() {
        Log.info("----Test: negativeSearchTrainsOneWayTicket02 - origin is empty ");
        homePage.choseDestinationStation("Netanya")
                .clickOnSearchButton();
        searchTrainsExceptionPage.waitForSearchTrainsExceptionIsLoadedTime50();
        Assert.assertTrue(searchTrainsExceptionPage.isOnSearchTrainsExceptionPage()
                &&searchTrainsExceptionPage.isErrorSummaryCorrect(1)
                &&searchTrainsExceptionPage.isErrorN1Correct("origin"), "Not passed. Some verificatios are not passed.");
    }

    @Test(groups = "regression")
    public void negativeSearchTrainsOneWayTicket03() {
        Log.info("----Test: negativeSearchTrainsOneWayTicket03 - destintion is empty ");
        homePage.choseOriginStation("Ako")
                .clickOnSearchButton();
        searchTrainsExceptionPage.waitForSearchTrainsExceptionIsLoadedTime50();
        Assert.assertTrue(searchTrainsExceptionPage.isOnSearchTrainsExceptionPage()
                &&searchTrainsExceptionPage.isErrorSummaryCorrect(1)
                &&searchTrainsExceptionPage.isErrorN1Correct("destination"), "Not passed. Some verificatios are not passed.");
    }

    @Test(groups = "regression")
    public void negativeSearchTrainsOneWayTicket04() {
        Log.info("----Test: negativeSearchTrainsOneWayTicket02 - origin and destination stations are the same ");
        homePage.choseOriginStation("Sderot")
                .choseDestinationStation("Sderot")
                .fillDateField(1)
                .clickOnSearchButton();
        searchTrainsExceptionPage.waitForSearchTrainsExceptionIsLoadedTime50();
        Assert.assertTrue(searchTrainsExceptionPage.isOnSearchTrainsExceptionPage()
                &&searchTrainsExceptionPage.isErrorSummaryCorrect(1), "Not passed. Some verificatios are not passed.");
    }

    @Test(groups = "regression")
    public void negativeSearchTrainsOneWayTicket05() {
        Log.info("----Test: negativeSearchTrainsOneWayTicket02 - date of oneWayTicket expired ");
        homePage.choseOriginStation("Haifa-Bat Galim")
                .choseDestinationStation("Hertsliya")
                .fillDateField(-10)
                .clickOnSearchButton();
        searchTrainsExceptionPage.waitForSearchTrainsExceptionIsLoadedTime50();
        Assert.assertTrue(searchTrainsExceptionPage.isOnSearchTrainsExceptionPage()
                &&searchTrainsExceptionPage.isErrorSummaryCorrect(1)
                &&searchTrainsExceptionPage.isErrorN1Correct("The date selected has passed"),"Not passed. Some verificatios are not passed.");
    }




}
