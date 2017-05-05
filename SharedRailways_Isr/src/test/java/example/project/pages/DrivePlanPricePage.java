package example.project.pages;

import example.project.LogLog4j;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by Inka on 04-May-17.
 */
public class DrivePlanPricePage extends Page {
    private static Logger Log = Logger.getLogger(LogLog4j.class.getName());

    @FindBy (xpath = "//*[@id='tblTickets']//label[contains(text(),'Single')]/..")
    WebElement radioButtonSingle;

    @FindBy (xpath = "//*[@id='tblTickets']//label[contains(text(),'Return')]/..")
    WebElement radioButtonReturn;

    @FindBy (xpath = "//*[@id='tblTickets']//label[contains(text(),'Weekly Travelcard')]/..")
    WebElement radioButtonWeeklyTravelCard;

    @FindBy (xpath = "//*[@id='tblTickets']//label[contains(text(),'Monthly Travelcard')]/..")
    WebElement radioButtonMonthlyTravelCard;

    @FindBy (xpath = "//*[@id='tblTickets']//label[contains(text(),'Journey Ticket')]/..")
    WebElement radioButton12JourneyTicket;

    @FindBy (xpath = "//*[@id='radChooseTicket112']/..")
    WebElement radioButtonEmpty;

    @FindBy (xpath = "//*[@id='tblTickets']//label[contains(text(),'Flexible')]/..")
    WebElement radioButtonFlexible30;

    @FindBy (id = "tblPrevious")
    WebElement previousStepButton;

    @FindBy (id = "tblNext")
    WebElement nextStepButton;

    public DrivePlanPricePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    //methods
    public DrivePlanPricePage waitForDrivePlanPriceIsLoadedTime50(){
        waitUntilIsLoadedCustomTime(previousStepButton, 50);
        waitUntilIsLoadedCustomTime(nextStepButton, 50);
        return this;
    }

    //verifications
    public boolean isOnDrivePlanPricePage (){
        Log.info("Is Drive Plan - Price page");
        return (this.exists(radioButtonSingle) && this.exists(radioButtonReturn));
    }
}