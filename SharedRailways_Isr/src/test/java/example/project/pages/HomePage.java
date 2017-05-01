package example.project.pages;

import example.project.DateUtil;
import example.project.LogLog4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.apache.log4j.Logger;
/**
 * Created by Inka on 28-Apr-17.
 */
public class HomePage extends Page {
    private static Logger Log = Logger.getLogger(LogLog4j.class.getName());

    /*@FindBy (id = "ctl00_PlaceHolderMain_ucSmallDrivePlan_cmbOriginStation")
    WebElement originField;*/
    @FindBy (xpath = "//div [@class ='clearfix'][1]/select")
    WebElement originField;


    /*@FindBy (id = "ctl00_PlaceHolderMain_ucSmallDrivePlanUC_cmbDestStation")
    WebElement destinationField;*/

    @FindBy (xpath = "//div [@class =\"clearfix\"][2]/select")
    WebElement destinationField;

    @FindBy (id = "ctl00_PlaceHolderMain_ucSmallDrivePlan_dpGoingTrain2_dateInput_text")
    WebElement dateField;

    @FindBy (id = "ctl00_PlaceHolderMain_ucSmallDrivePlan_dpGoingTrain2_popupButton")
    WebElement calendarButton;

    @FindBy (id = "ctl00_PlaceHolderMain_ucSmallDrivePlan_GoingHourDeparture")
    WebElement departureCheckBox;

    @FindBy (id = "ctl00_PlaceHolderMain_ucSmallDrivePlan_ArrivalHourDeparture")
    WebElement arrivalCheckBox;

    @FindBy (id = "ctl00_PlaceHolderMain_ucSmallDrivePlan_ctlDepartureTime")
    WebElement departureTimeField;

    // returning train elements

    @FindBy (id = "ctl00_PlaceHolderMain_ucSmallDrivePlan_retInfoHref")
    WebElement returningTrainLink;

    @FindBy (id = "ctl00_PlaceHolderMain_ucSmallDrivePlan_dpReturnningTrain2_dateInput_text")
    WebElement returningDateField;

    @FindBy (id = "ctl00_PlaceHolderMain_ucSmallDrivePlan_GoingHourReturn")
    WebElement returningTrainDepartureCheckBox;

    @FindBy (id = "ctl00_PlaceHolderMain_ucSmallDrivePlan_ArrivalHourReturn")
    WebElement returningTrainArrivalCheckBox;

    @FindBy (id = "ctl00_PlaceHolderMain_ucSmallDrivePlan_tdSendCenterArea")
    WebElement searchButton;

    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    //methods
    public HomePage choseOriginStation(String text){
        Log.info("Choosing Origin Station: " + text);
        this.selectValueInDropdownbyText(this.originField, text);
        return this;
    }

    public HomePage choseDestinationStation(String text){
        Log.info("Choosing Destination Station: " + text);
        this.selectValueInDropdownbyText(this.destinationField, text);
        return this;
    }

    public HomePage fillDateField (int  plusDays){
        Log.info("Filling data field for one way ticket");
        String data = DateUtil.currentDatePlusDays(plusDays);
        this.setElementText(dateField,data);
        return this;
    }
     public HomePage clickDepartureOrArrivalCheckBox(String checkBoxType){
        if (checkBoxType=="D") {
        Log.info("Clicking departure or arrival check box for one way ticket: departure");
        this.clickElement(departureCheckBox);}
        else if (checkBoxType=="A"){
            Log.info("Clicking departure or arrival check box for one way ticket: arrival");
            this.clickElement(arrivalCheckBox);
            }
            else Log.info("Clicking departure or arrival check box for one way ticket: nothing");
        return this;
     }

    public  HomePage choseDepartureTime(String time){
        Log.info("Choosing departure time for one way ticket");
        this.selectValueInDropdownbyText(departureTimeField,time);
        return this;
    }

    public HomePage clickOnSearchButton(){
        Log.info("Clicking on Search button");
        this.clickElement(searchButton);
        return this;
    }

    public HomePage waitForHomePageIsLoadedTime50() {
        Log.info("Waiting for home page (time 50)");
        waitUntilIsLoadedCustomTime(originField, 50);
        waitUntilIsLoadedCustomTime(searchButton, 50);
        return this;
    }

    public boolean isOnHomePage(){
        Log.info("Verification: Is on Home Page (originFields, destinationField and SearchButton elements exist)");
        return (exists(originField)&&exists(destinationField)&&exists(searchButton));
    }


}
