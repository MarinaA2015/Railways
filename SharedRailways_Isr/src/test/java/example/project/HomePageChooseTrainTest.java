package example.project;

import example.project.pages.DrivePlanChooseTrainPage;
import example.project.pages.DrivePlanPricePage;
import example.project.pages.HomePage;
import example.project.pages.SearchTrainsExceptionPage;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.selenium.factory.WebDriverPool;

import java.util.List;

//import java.time.LocalDate;

/**
 * Created by Inka on 28-Apr-17.
 */
public class HomePageChooseTrainTest extends TestNgTestBase {
    private static final Integer MAXIMUM_IN_LIST = 6;
    private static Logger Log = Logger.getLogger(LogLog4j.class.getName());
    private static final String URL_LINK =
            "http://www1.rail.co.il/EN/Pages/Homepage.aspx";


    private HomePage homePage;
    private DrivePlanChooseTrainPage drivePlanChooseTrainPage;
    private SearchTrainsExceptionPage searchTrainsExceptionPage;
    private DrivePlanPricePage drivePlanPricePage;
    String originStation = "Lod";
    String numberDays ="1";
    String destinationStation = "Ashkelon";
    String checkBox ="A";
    String time = "12:00";
    int plusDays = Integer.parseInt(numberDays);

   // LocalDate currentDate = LocalDate.now();


     //---------------

    @BeforeMethod(alwaysRun = true)
    public void initPageObjects() {
        Log.info("Before Method for HomePageChooseTrainTest. Data for train choosing: "+originStation+", "+destinationStation+", "+checkBox+", "+plusDays+", "+time);
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

    /*@Test(groups = {"smoke", "regression"})
    public void negativeSearchTrainsOneWayTicket_ChooseTrain01() {
        //System.out.println(plusDays+1);

        Log.info("----Test: negativeSearchTrainsOneWayTicket_ChooseTrain - train wasn't chosen ");

        homePage.choseOriginStation(originStation)
                .choseDestinationStation(destinationStation)
                .fillDateField(plusDays)
                .clickDepartureOrArrivalCheckBox(checkBox)
                .choseDepartureTime(time)
                .clickOnSearchButton();
        drivePlanChooseTrainPage.waitForDrivePlanChooseTrainIsLoadedTime50()
                //.findAndWaitAllTrains(MAXIMUM_IN_LIST)
                //.moveMouthCursorOverFirstTrainInTable()
                .clickOnNextStepButton();
        searchTrainsExceptionPage.waitForSearchTrainsExceptionIsLoadedTime50();
        Assert.assertTrue(searchTrainsExceptionPage.isOnSearchTrainsExceptionPage()
                &&searchTrainsExceptionPage.isErrorSummaryCorrect(1),"Not passed. Some verificatios are not passed.");


    }*/

    //Choosing and clicking on each train in the list and verification that the system displays next screen correctly
    @Test(groups = "regression")
    public void positiveSearchTrainsOneWayTicket_ChooseEachTrain() {
        List<WebElement> listOfTrains;
        Log.info("----Test: positiveSearchTrainsOneWayTicket_ChooseEachTrain");
        homePage.choseOriginStation(originStation)
                .choseDestinationStation(destinationStation)
                .fillDateField(plusDays)
                .clickDepartureOrArrivalCheckBox(checkBox)
                .choseDepartureTime(time)
                .clickOnSearchButton();
        drivePlanChooseTrainPage.waitForDrivePlanChooseTrainIsLoadedTime50()
                               // .findAllTrains();
                                .findAllTrains(MAXIMUM_IN_LIST);
        List <WebElement> trains = drivePlanChooseTrainPage.getListOfTrains();
        Log.info("trains.size: "+trains.size());
        int numberOfTrain=0;
        Boolean flag = true;
        if (trains.size()==0) Log.info("List of trains is empty");
        for (WebElement element: trains){
            numberOfTrain++;

           drivePlanChooseTrainPage.chooseTrain(element)
                                    .clickOnNextStepButton();

           drivePlanPricePage.waitForDrivePlanPriceIsLoadedTime50();

           if (!drivePlanPricePage.isOnDrivePlanPricePage()) flag=false;
           Log.info(flag);

           drivePlanPricePage.clickOnPreviousStepButton();
           drivePlanChooseTrainPage.waitForDrivePlanChooseTrainIsLoadedTime50();
        }
        Assert.assertTrue(flag&&(numberOfTrain==trains.size()));
    }

    @Test(groups = {"smoke", "regression"})
    public void positiveSearchTrainsOneWayTicket_ChooseTrain() {
        //System.out.println(plusDays+1);
        Log.info("----Test: positiveSearchTrainsOneWayTicket_ChooseTrain");

        homePage.choseOriginStation(originStation)
                .choseDestinationStation(destinationStation)
                .fillDateField(plusDays)
                .clickDepartureOrArrivalCheckBox(checkBox)
                .choseDepartureTime(time)
                .clickOnSearchButton();
        drivePlanChooseTrainPage.waitForDrivePlanChooseTrainIsLoadedTime50()
                //.moveMouthCursorOverFirstTrainInTable()
                //.clickOnSecondTrainInTheTable()
                //.clickOnThirdTrainInTheTable()
                .findAllTrains(MAXIMUM_IN_LIST)
                .chooseAnyTrainFromTheList()
                .clickOnNextStepButton();
        drivePlanPricePage.waitForDrivePlanPriceIsLoadedTime50();
                            //.clickOnPreviousStepButton();
        //drivePlanChooseTrainPage.waitForDrivePlanChooseTrainIsLoadedTime50();
        Assert.assertTrue(drivePlanPricePage.isOnDrivePlanPricePage());


    }


}
