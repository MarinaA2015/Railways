package example.project.pages;

import example.project.DateUtil;
import example.project.LogLog4j;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Inka on 29-Apr-17.
 */
public class DrivePlanChooseTrainPage extends Page {
    private static Logger Log = Logger.getLogger(LogLog4j.class.getName());
    private    List<WebElement> listOfTrains;

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

    @FindBy(id = "2")
    WebElement secondTrainInTheTable;


    @FindBy(id = "3")
    WebElement thirdTrainInTheTable;

    @FindBy(id = "A1")
    WebElement nextStepButton;







    public DrivePlanChooseTrainPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this);

    }

    //methods
    public DrivePlanChooseTrainPage waitForDrivePlanChooseTrainIsLoadedTime50() {
        Log.info("Waiting for Drive Plan - Choose Train (time 50)");
        waitUntilIsLoadedCustomTime(nextStepButton, 50);
        return this;
    }
    public List<WebElement> getListOfTrains(){
        return this.listOfTrains;
    }

    public DrivePlanChooseTrainPage chooseAnyTrainFromTheList(){
        Log.info("Choosing any train from the list");
        Random rand = new Random();
        int sizeListOfTrains = this.listOfTrains.size();
        if (this.listOfTrains.size()==0) Log.info("There are no trains in the list");
        else {
            int  n = rand.nextInt(sizeListOfTrains)+1;
            int counter=0;
            for(WebElement element: listOfTrains){
                counter++;
                if (counter==n) {
                    chooseTrain(element);
                    return this;
                }
            }
        }
        return this;
    }

   /* public DrivePlanChooseTrainPage clickOnSecondTrainInTheTable(){
        Log.info("Clicking on the first train in the table");
        clickElement(secondTrainInTheTable);
        return this;
    }

    public DrivePlanChooseTrainPage clickOnThirdTrainInTheTable(){
        Log.info("Clicking on the first train in the table");
        clickElement(thirdTrainInTheTable);
        return this;
    }*/

    public DrivePlanChooseTrainPage clickOnNextStepButton(){
        Log.info("Clicking on the Next Step button");
        clickElement(nextStepButton);
        return this;
    }



    //-----------------------------------
    // --- Finds all visible train records and adds them to this.listOfTrains (List<WebElement>)
    public DrivePlanChooseTrainPage findAllTrains(Integer maximumInTheList){
        Log.info(" Receiving list of trains");
        this.listOfTrains = new ArrayList<WebElement>();
        for (int i=0; i<maximumInTheList; i++){

            if(this.isElementPresent(By.id(""+i))){
                //System.out.println(i+" element exists");
                listOfTrains.add(driver.findElement(By.id(""+i)));
            }
        }
        Log.info(" The quantity of trains in the list: "+listOfTrains.size());
        return this;
    }
    //--------------------------------

    public DrivePlanChooseTrainPage chooseTrain(WebElement train){
        Log.info("Choosing train in the list");
        //Log.info(train.getAttribute("class"));
        clickElement(train);
        //Log.info(train.getAttribute("class"));
        waitUntilIsAttributeCustomTime(train,"class","selected",45);
        return this;
    }



    //Verifications
    public boolean isOnDrivePlanChooseTrain (){
        Log.info("Is Drive Plan - Choose Train page");
        return (this.exists(chooseStationLabel)&&this.exists(nextStepButton));
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
