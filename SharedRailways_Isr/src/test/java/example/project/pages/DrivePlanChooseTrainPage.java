package example.project.pages;

import example.project.DateUtil;
import example.project.LogLog4j;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by Inka on 29-Apr-17.
 */
public class DrivePlanChooseTrainPage extends Page {
    private static Logger Log = Logger.getLogger(LogLog4j.class.getName());

    @FindBy(xpath = "//*[@id='divOneWay']//*[@class='directionTo']/h3")
    WebElement oneWayLabel;

    @FindBy(xpath = "//*[@id='divOneWay']//*[@class='directionTo']/h4")
    WebElement oneWayDate;

    @FindBy(id = "ctl00_PlaceHolderMain_ucTicketWizard_OneWaySourceStation")
    WebElement fromOriginStation;

    @FindBy(id = "ctl00_PlaceHolderMain_ucTicketWizard_OneWayDestinationStation")
    WebElement toDestinationStation;

    @FindBy(id = "stationTab_2")
    WebElement chooseStationLabel;

    public DrivePlanChooseTrainPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this);
    }

    //methods
    public DrivePlanChooseTrainPage waitForDrivePlanChooseTrainIsLoadedTime50() {
        Log.info("Waiting for Drive Plan - Choose Train (time 50)");
        waitUntilIsLoadedCustomTime(oneWayLabel, 50);
        waitUntilIsLoadedCustomTime(oneWayDate, 50);
        return this;
    }
    //Verifications
    public boolean isOnDrivePlanChooseTrain (){
        Log.info("Is Drive Plan - Choose Train page");
        return this.exists(chooseStationLabel);
    }
    public boolean isOneWayTicket(){
        boolean flag = this.exists(oneWayLabel)&&oneWayLabel.getText().contains("One way");
        Log.info("Verification - is one way ticket: "+flag);
        return flag;
    }

    public boolean isCorrectOneWayDate(int plusDays){
        String text = DateUtil.currentDatePlusDays(plusDays);
        boolean flag = oneWayDate.getText().toLowerCase().contains(text.toLowerCase());
        Log.info("Verification - is one way date correct: "+flag);
        return flag;
    }

    public boolean isCorrectOriginStation(String text){
        boolean flag = fromOriginStation.getText().toLowerCase().contains(text.toLowerCase());
        Log.info("Verification - is origin station correct: "+flag);
        return flag;
    }

    public boolean isCorrectDestinationStation(String text){
        boolean flag = toDestinationStation.getText().toLowerCase().contains(text.toLowerCase());
        Log.info("Verification - is destintion station correct: "+flag);
        return flag;
    }

}
