package example.project.pages;

import example.project.LogLog4j;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


/**
 * Created by Inka on 30-Apr-17.
 */


public class SearchTrainsExceptionPage extends Page {
    private static Logger Log = Logger.getLogger(LogLog4j.class.getName());

    @FindBy(id = "popup_title")
    WebElement  titleException;

    @FindBy(id = "errorsummary")
    WebElement errorSummary;

    @FindBy(xpath = "//*[@id='popup_message']//li[1]/a")
    WebElement errorN1;

    @FindBy(xpath = "//*[@id='popup_message']//li[2]/a")
    WebElement errorN2;

    @FindBy(xpath = "//*[@id='popup_ok']/span")
    WebElement backToFormLink;

    public SearchTrainsExceptionPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this);
    }
    //methods
    public SearchTrainsExceptionPage waitForSearchTrainsExceptionIsLoadedTime50() {
        Log.info("Waiting for search trains exception is loaded (time 50)");
        waitUntilIsLoadedCustomTime(errorSummary, 50);
        return this;
    }

    public SearchTrainsExceptionPage clickOnBackToForm(){
        Log.info("Clicking to 'Back to form' link");
        this.clickElement(backToFormLink);
        return this;
    }

    //Verifications
    public boolean isOnSearchTrainsExceptionPage(){
        boolean flag = this.exists(titleException);
        Log.info("Is on search trains exception verifiction: "+flag);
        return flag;
    }

    public boolean isErrorSummaryCorrect(Integer num){
        boolean flag = errorSummary.getText().contains(num.toString());
        Log.info("Error summary contains "+num+": "+flag);
        return flag;
    }

    public boolean isErrorN1Correct(String text){
        boolean flag = errorN1.getText().toLowerCase().contains(text.toLowerCase());
        Log.info("Error N1 in the list contins "+text+": "+flag);
        return flag;
    }

    public boolean isErrorN2Correct(String text){
        boolean flag = errorN2.getText().toLowerCase().contains(text.toLowerCase());
        Log.info("Error N2 in the list contins "+text+": "+flag);
        return flag;
    }
}
