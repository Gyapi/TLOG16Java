/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Entities.Task;
import Entities.TimeLogger;
import Entities.WorkDay;
import Entities.WorkMonth;
import Exceptions.EmptyTimeFieldException;
import Exceptions.FutureWorkException;
import Exceptions.InvalidTaskIdException;
import Exceptions.NegativeMinutesOfWorkException;
import Exceptions.NoTaskIdException;
import Exceptions.NotExpectedTimeOrderException;
import Exceptions.NotNewDateException;
import Exceptions.NotNewMonthException;
import Exceptions.NotSeparatedTimesException;
import Exceptions.NotTheSameMonthException;
import Exceptions.WeekendNotEnabledException;
import Util.Util;
import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * This class creates a basic, text based User interface
 * to the console, trough wich, the application becomes
 * usable
 * <br>Kinda beta test version of the app
 * @author Gyapi
 */
public class TimeLoggerUI {
    
    private static TimeLogger timeLogger;
    
    /**
     * 
     * @param args 
     */
    public static void main(String[] args) {
        timeLogger = new TimeLogger();
        
        //-----------------Test code-----------------
        //TODO : DELETE after obsolete
        Task testTask1, testTask2, testTask3, testTask4, testTask5, 
                testTask6, testTask7, testTask8, testTask9, testTask10, testTask11,
                testTask12, testTask13;
        WorkMonth testMonth1, testMonth2;
        WorkDay testDay1, testDay2, testDay3, testDay4, testDay5;
        
        try {
            testMonth1 = new WorkMonth(2017, 10);
            testTask1 = new Task("LT-0001", "Exception Test", "07:30", "07:45");
            testTask2 = new Task("LT-0002", "Exception Test", "08:30", "08:45");
            testDay1 = new WorkDay(120, 2017, 10, 10);
            testDay1.addTask(testTask1);
            testDay1.addTask(testTask2);
            testMonth1.addWorkDay(testDay1);
            testTask3 = new Task("LT-0003", "Exception Test", "07:30", "07:45");
            testTask4 = new Task("LT-0004", "Exception Test", "08:30", "08:45");
            testDay2 = new WorkDay(2017, 10, 11);
            testDay2.addTask(testTask3);
            testDay2.addTask(testTask4);
            testMonth1.addWorkDay(testDay2);   

            timeLogger.addMonth(testMonth1);
            
            testMonth2 = new WorkMonth(2017, 9);
            testTask5 = new Task("LT-0005", "Exception Test", "07:30", "07:45");
            testTask6 = new Task("LT-0006", "Exception Test", "08:30", "08:45");
            testDay3 = new WorkDay(120, 2017, 9, 8);
            testDay3.addTask(testTask5);
            testDay3.addTask(testTask6);
            testMonth2.addWorkDay(testDay3);

            testTask7 = new Task("LT-0007", "Exception Test", "07:30", "07:45");
            testTask8 = new Task("LT-0008", "Exception Test", "08:30", "08:45");
            testDay4 = new WorkDay(2017, 9, 11);
            testDay4.addTask(testTask7);
            testDay4.addTask(testTask8);
            testMonth2.addWorkDay(testDay4);

            testTask9 = new Task("LT-0009", "Exception Test", "07:30", "07:45");
            testTask10 = new Task("LT-0010", "Exception Test", "08:30", "08:45");
            testTask11 = new Task("LT-0011");
            testTask11.setStartTime("08:45");
            testTask11.setComment("Unfinished Test");  
            testTask12 = new Task("LT-0012", "Exception Test", "10:30", "10:45"); 
            testTask13 = new Task("LT-0013");
            testTask13.setStartTime("07:15");
            testTask13.setComment("Unfinished Test"); 
            testDay5 = new WorkDay(2017, 9, 12);
            testDay5.addTask(testTask9);
            testDay5.addTask(testTask10);
            testDay5.addTask(testTask11);
            testDay5.addTask(testTask12);
            testDay5.addTask(testTask13);
            testMonth2.addWorkDay(testDay5);

            timeLogger.addMonth(testMonth2);
            System.out.println(timeLogger.getMonths().size());
        } 
        catch (EmptyTimeFieldException | FutureWorkException | InvalidTaskIdException |
                NegativeMinutesOfWorkException | NoTaskIdException | NotExpectedTimeOrderException | 
                NotNewDateException | NotNewMonthException | NotSeparatedTimesException | 
                NotTheSameMonthException | WeekendNotEnabledException exception) {
            System.out.println(exception);
        }
        //---------------------------------------------
        
        menuToConsole();
    }
    

    /**
     * 
     * Main Menu Display
     * <br>Displays the applications functions to the console
     * in a menu format
     * <br>After displaying the menu, calls the {@link #selectItem() selectItem} method
     */
    private static void menuToConsole() {
        System.out.println("Please choose from the following options:"
                + "\n0. Exit"
                + "\n1. List months"
                + "\n2. List days"
                + "\n3. List tasks for a specific day"
                + "\n4. Add new month"
                + "\n5. Add day to a specific month"
                + "\n6. Start a task for a day"
                + "\n7. Finish a specific task"
                + "\n8. Delete a specific task"                
                + "\n9. Modify task"
                + "\n10. Statistics");
        selectItem();
    }
    
    /**
     * 
     * Handles the selection of menu items (trought {@link #consoleReader() consoleReader}
     * method, and cals the selected method
     */
    private static void selectItem() {        
        switch (consoleReader()){
            case "0": terminate();
            break;
            case "1": monthList();   
                backToMainMenu();
            break;
            case "2": dayList();
            break;
            case "3":       
                System.out.println("Plese enter the requred date in the following format:"
                        + "\nmonthID (with at least 2 digit numbers)-day(with 2 digit numbers)" 
                        + "\n example: 09-11"
                        + "\n You can get the id of the month from the 'List months' menu item of the main menu");
                taskList();
            break;
            case "4":         
                System.out.println("Plese enter the new month in the following formats:"
                        + "\n year-month(with 2 digit numbers)"
                        + "\n example: 2017-01");                
                addMonth();
            break;
            case "5": addDay();
            break;
            case "6": startTask();
            break;
            case "7": finishTask();
            break;
            case "8": deleteTask();
            break;
            case "9": modifyTask();
            break;
            case "10": statistics();
            break;
            default: 
                System.out.println("Invalid menu item, please try again.");
                selectItem();
            break;
            
        }
    }
    
    /**
     * 
     * Reads a line of String from the console
     * @return : {@link String String} the entered line
     */
    private static String consoleReader(){
        
        Scanner selected = new Scanner(System.in); 
        String input = null;
        
        input = selected.nextLine();
        
        if(input.isEmpty()){
            input = " ";
        }
        
        return input;
    }
    
    /**
     * 
     * Forces the user to write a number into the console
     * @return : {@link Integer Integer} the number read from the console
     */
    private static int makeItInteger(){
        int chosen;
        
        try{
            chosen = Integer.parseInt(consoleReader());
        }
        catch (NumberFormatException ex){
            System.out.println(ex + ": Not a number. Please try again");
            chosen = makeItInteger();
        }
        
        return chosen;
    }
    
    /**
     * 
     * Redirects the user to the Main Menu, or closes the application.
     */
    private static void backToMainMenu(){
        
        System.out.println("\nPlease press any key, then Enter to return to the Main Menu, "
                + "or the '0' key than Enter to exit the application.");
        switch (consoleReader()){
            case "0": terminate();
            break;
            default: menuToConsole();
            break;
        }
        
    }

    /**
     * 
     * Exits the application
     */
    private static void terminate() {
        System.out.println("Closing application...");
        System.exit(0);
    }

    /**
     * 
     * Lists months with the format: (position in the list). year-month
     * <br>At the end of it's lifespan, calls the {@link #backToMainMenu() backToMainMenu} method
     */
    private static void monthList() {
        
        List<WorkMonth> months = timeLogger.getMonths();
        
        months.forEach((month) -> {
            System.out.println(months.indexOf(month) + 1 + ". " + 
                    month.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM"))
            );
        });       
    }

    /**
     *
     * Lists the dayst in the selected month
     * <br> uses the {@link #monthList() monthList}, {@link #makeItInteger() makeItInteger} methods
     * <br>At the end of it's lifespan, calls the {@link #backToMainMenu() backToMainMenu} method
     */
    private static void dayList() {
        
        int chosenMonth;
        List<WorkDay> days;      
        
        System.out.println("Please choose a moth:");
        monthList(); 
        chosenMonth = makeItInteger();
        while (chosenMonth == 0 || chosenMonth > timeLogger.getMonths().size()){
            System.out.println("Invalid month id. Please select a valid id from the list of months.");
            chosenMonth = makeItInteger();            
        }

        days = timeLogger.getMonths().get(chosenMonth - 1).getDays();
        days.forEach((day) -> {
            System.out.print(day.getActualDay().format(DateTimeFormatter.ofPattern("MM.dd")) + ", ");
        });
        
        backToMainMenu();
        
    }

    /**
     * 
     * Lists tasks for the chosen day
     * <br>At the end of it's lifespan, calls the {@link #backToMainMenu() backToMainMenu} method
     */
    private static void taskList() {
        
        int month, selectedDay;
        List<WorkDay> filteredDay;
                
        try{        
            String[] parts = consoleReader().split("-");            
            month = Integer.parseInt(parts[0]);
            selectedDay = Integer.parseInt(parts[1]);
            
            if(month > timeLogger.getMonths().size() || month == 0){
                System.out.println("Please chose an existing month"
                + "\n You can get the id of the month from the 'List months' menu item of the main menu");
                taskList();
            }
            else{        
                filteredDay = timeLogger.getMonths().get(month - 1).getDays().stream()
                        .filter(day -> day.getActualDay().getDayOfMonth() == selectedDay)
                        .collect(Collectors.toList());
                
                if (filteredDay.isEmpty()){
                    System.out.println("Please chose an existing day"
                    + "\n You can get the id of the month from the 'List days' menu item of the main menu");
                    taskList();                    
                }
                else{
                    filteredDay.get(0).getTasks().forEach((task) -> {
                        System.out.println(task.toString());
                    });
                }
            }
        }
        catch (NumberFormatException ex){
            System.out.println(ex + ": Wrong format, please try again");
            taskList();
        }
        
        backToMainMenu();
        
    }

    /**
     * 
     * Adds a new month to the list of months 
     * <br>At the end of it's lifespan, calls the {@link #backToMainMenu() backToMainMenu} method
     */    
    private static void addMonth() {
        
        try{
            String[] parts = consoleReader().split("-");
            
            WorkMonth m = new WorkMonth(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
            timeLogger.addMonth(m);  
            System.out.println("The new month (" + m.getDate().getYear() 
                    + "-" + m.getDate().getMonthValue()+ ") sucessfully added.");
        }
        catch (NumberFormatException | DateTimeException ex){
            System.out.println(ex + ": Wrong format, please try again");
            addMonth();
        }
        catch (NotNewMonthException ex){
            System.out.println(ex);
            addMonth();
        }
        
        backToMainMenu();
    }

    /**
     * 
     * Adds a new WorkDay to the selected month
     * <br>At the end of it's lifespan, calls the {@link #backToMainMenu() backToMainMenu} method
     */
    private static void addDay() {
        
        int monthIndex, date, requiredWorkHour;
        WorkDay wd;

        System.out.println("Please type the idex of the month you want to add a day to:");
        monthList(); 
        
        monthIndex = makeItInteger();        
        while (monthIndex > timeLogger.getMonths().size() || monthIndex == 0){
            System.out.println("Invalid month id. Please select a valid id from the list of months.");
            monthIndex = makeItInteger();
        }
        
        System.out.println("Please type the date of the day you want to add in a 2 digit format.");
        date = makeItInteger();
        while (date == 0 || date > timeLogger.getMonths().get(monthIndex - 1).getDate().lengthOfMonth()){
            System.out.println("That date does not exist in the selected month. Please try again.");
            date = makeItInteger();
        }

        System.out.println("Pleasy type the required work hours for the day(in minutes."
                + "Or, press 0 and enter to set up the default value (7.5 hours / 450 minutes)");
        requiredWorkHour = makeItInteger();
        while (requiredWorkHour > 1440){
            System.out.println("You can't work more than 24 hours a day. Please try again.");
            requiredWorkHour = makeItInteger();
        }
        
        try{
            if (requiredWorkHour == 0){
                wd = new WorkDay(timeLogger.getMonths().get(monthIndex - 1).getDate().getYear(),
                        timeLogger.getMonths().get(monthIndex - 1).getDate().getMonthValue(), date);
            }
            else{
                wd = new WorkDay(requiredWorkHour, timeLogger.getMonths().get(monthIndex - 1).getDate().getYear(),
                        timeLogger.getMonths().get(monthIndex - 1).getDate().getMonthValue(), date);
            }
            
            timeLogger.getMonths().get(monthIndex - 1).addWorkDay(wd);
            System.out.println("New workday ( " + wd.getActualDay().getYear() + "." + wd.getActualDay().getMonthValue()
                    + "." + wd.getActualDay().getDayOfMonth() + ") susessfully added to the month: " + 
                    timeLogger.getMonths().get(monthIndex - 1).getDate().getYear() + "." 
                    + timeLogger.getMonths().get(monthIndex - 1).getDate().getMonthValue());
        }
        catch (FutureWorkException | NegativeMinutesOfWorkException | NotNewDateException |
                NotTheSameMonthException | WeekendNotEnabledException ex){
            System.out.println(ex);
            addDay();
        }
        
        backToMainMenu();
    }
    
    /**
     * 
     * Starts a new task (without endTime)
     * <br>Uses the {@link #giveMeADay(java.util.List, int) giveMeADay},
     * {@link #createTheTask() createTheTask}, 
     * {@link #addTask(Entities.WorkDay, Entities.Task) addTask} methods
     * <br>At the end of it's lifespan, calls the {@link #backToMainMenu() backToMainMenu} method
     */
    private static void startTask() {
                
        int monthIndex, dayDate;
        List<WorkDay> days; 
        WorkDay selected;
        Task task;

        System.out.println("Please type the index of the month you want to add a day to:");
        monthList(); 
        
        monthIndex = makeItInteger();        
        while (monthIndex > timeLogger.getMonths().size() || monthIndex == 0){
            System.out.println("Invalid month id. Please select a valid id from the list of months.");
            monthIndex = makeItInteger();
        }
        
        System.out.println("Please type the date of the day you want to start a task with 2 digits."
                + "Example: 02");
        days = timeLogger.getMonths().get(monthIndex - 1).getDays();
        days.forEach((day) -> {
            System.out.print(day.getActualDay().format(DateTimeFormatter.ofPattern("MM.dd")) + ", ");
        });
        System.out.println("");
        dayDate = makeItInteger();
        selected = giveMeADay(days, dayDate);
       
        System.out.println("Please enter the ID of the task. "
        + "\n Please bear in mind that the task ID must be in the correct format"
        + "\nRedmine ID: 4 digits, LT ID: LT-4digits");
        
        task = createTheTask();        
        addTask(selected, task);
        
        //backToMainMenu();
    }
        
    /**
     * 
     * Checks if the given {@link WorkDay date} exists in the given {@link ArrayList list}
     * @param days : {@link ArrayList list} of {@link WorkDay workdays}
     * @param Date : {@link Integer int} the day of the month value of the selected day
     * @return : the selected {@link WorkDay workDay}
     */
    private static WorkDay giveMeADay(List<WorkDay> days, int Date){

     WorkDay selected;

        try{
            selected = days.stream()
                            .filter(day -> day.getActualDay().getDayOfMonth() == Date)
                            .findFirst()
                            .get();
        }
        catch(Exception ex){
            System.out.println("The chosen date does not exist. Please try again or"
                    + " return to the main menu and add it to the list.");  
            selected = giveMeADay(days, makeItInteger());
        }
        return selected;
    }

    /**
     * 
     * Creates a {@link Task task} after reading it's ID from the console
     * @return : the created {@link Task task}
     */
    private static Task createTheTask(){
        Task task;
        try{
            task = new Task(consoleReader());
        }
        catch (InvalidTaskIdException | NoTaskIdException ex){
            System.out.println(ex);
            task = createTheTask();
        }
        return task;
    }
    
    /**
     * 
     * Adds the created {@link Task task} to the given {@link ArrayList list} of tasks
     * <br>Uses the {@link #timer(Entities.WorkDay, Entities.Task) timer} method
     * @param day
     * @param task 
     */
    private static void addTask(WorkDay day, Task task){
        timer(day, task);
        try{
            day.addTask(task);
            System.out.println("The task added to the database. "
                    + "\nPlease do not roget to set it to finished when you are done.");
        }
        catch(NotSeparatedTimesException nse){
            System.out.println(nse 
                    + "\nThe time of the task is in collision with an other one. please try again");
            addTask(day, task);
        }
        catch (Exception ex){
            System.out.println(ex);
        }
    }
    
    /**
     * 
     * Sets the starTime of the given {@link Task task}
     * <br>If the given {@link WorkDay workDay} already has tasks in it's list,
     * ad the user enters a blank sapce, the method will give the last tasks 
     * endtime to the given task as startTime
     * <br>Uses the {@link #setIt(Entities.Task, java.lang.String) setIt} method
     * @param selected
     * @param task 
     */
    private static void timer(WorkDay selected, Task task){
        
        String startTime;
        
        try{
            if (!selected.getTasks().isEmpty()){
                System.out.println("WARNING! The selected day already has task(s)."
                        + "\nPlease make sure that the new task does not collide with them."
                        + "Please type the start time of the new task in the following format, then press enter:"
                        + "\n HH:mm"
                        + "\n example: 03:04"
                        + "\n Type in a space character only if you want it to start at the end time of the last task: "
                        + "{" + selected.endTimeOfTheLastTask() + "}"
                );
                startTime = consoleReader();
                if (startTime.equals(" ")){
                   task.setStartTime(selected.endTimeOfTheLastTask());
                }
                else{
                    setIt(task, startTime);
                }
            }
            else{
                System.out.println("Please type the start time of the new task in the following format, then press enter:"
                        + "\n HH:mm"
                        + "\n example: 03:04"); 
                startTime = consoleReader();
                setIt(task, startTime);             
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        
    }
        
    /**
     * 
     * Sets the startTime of the given {@link Task task}
     * @param task : {@link Task task}
     * @param startTime : {@link String string} read from the console
     */
    private static void setIt(Task task, String startTime){
        try{
            task.setStartTime(startTime);
        }
        catch(Exception ex){
            System.out.println(ex + "\n Wrong format. Please try again.");
            setIt(task, consoleReader());
        }
    } 

    /**
     * 
     * Lists the unfinished (haas no endTime) {@link Task tasks} of the selected {@link WorkDay day}
     * then finishes the one, the user choose.     
     * <br>uses the {@link #selectMe(java.util.List) selectMe}, {@link #EndTimer(Entities.Task) EndTimer} methods
     * <br>At the end of it's lifespan, calls the {@link #backToMainMenu() backToMainMenu} method
     */
    private static void finishTask() {
                
        int monthIndex, dayDate, selector = 0;
        List<WorkDay> days;
        WorkDay selected;
        List<Task> unFinished = new ArrayList<>();
        Task taskToEnd;

        System.out.println("Please type the index of the month you want to finish a task at:");
        monthList(); 
        
        monthIndex = makeItInteger();        
        while (monthIndex > timeLogger.getMonths().size() || monthIndex == 0){
            System.out.println("Invalid month id. Please select a valid id from the list of months.");
            monthIndex = makeItInteger();
        }
        
        System.out.println("Please type the date of the day you want to finish a task, with 2 digits."
                + "Example: 02");
        days = timeLogger.getMonths().get(monthIndex - 1).getDays();
        days.forEach((day) -> {
            System.out.print(day.getActualDay().format(DateTimeFormatter.ofPattern("MM.dd")) + ", ");
        });
        System.out.println("");
        dayDate = makeItInteger();
        selected = giveMeADay(days, dayDate);
        
        System.out.println("These are the unfinished tasks for the day:");
        for (Task task : selected.getTasks()) {
            if (task.getEndTime() == null){
                unFinished.add(task);
                selector++;
                System.out.println(selector + ". " + task.getTaskId() + " " + task.getStartTime());
            }
        }        
        System.out.println("Please select the task you want to finish:");
        taskToEnd = selectMe(unFinished);
        
        System.out.println("Please type the end time of the new task in the following format, then press enter:"
                        + "\n HH:mm"
                        + "\n example: 03:04"); 
        EndTimer(taskToEnd, selected.getTasks());
        System.out.println("Finsihing " + taskToEnd.getTaskId() + " successful.");
        
        backToMainMenu();        
    }
    
    /**
     * 
     * Gives back the {@link Task task} choosen by the user from the given {@link ArrayList list}
     * @param unFinished : {@link ArrayList ArrayList} to choose a {@link Task Task} from
     * @return : the selected {@link Task Task}
     */
    private static Task selectMe(List<Task> tasks){ 
        
        int selectedID = makeItInteger();   
        Task selected;
        
        try{
            selected = tasks.get(selectedID - 1);
        }
        catch(Exception ex){
            System.out.println("Invalid task, please try again.");
            selected = selectMe(tasks);
        }
        return selected;
    }

    /**
     * 
     * Sets the endTime of the given {@link Task task}
     * @param modify : {@link Task task}
     * @param tasks : a {@link ArrayList list} of tasks, to check if the end time does not collide.
     */
    private static void EndTimer(Task modify, List<Task> tasks){
        
        String endTime;
        Task clone = modify;
        
        endTime = consoleReader();
        try{
            clone.setEndTime(endTime);
            if (Util.isSeparatedTime(clone, tasks)){
                throw new NotSeparatedTimesException("The given end time collides with an another task."
                        + "Please try again.");
            }
            modify.setEndTime(endTime);
        }
        catch (NotSeparatedTimesException ex){
            System.out.println(ex);
            EndTimer(modify, tasks);
        }
        catch(Exception ex){
            System.out.println(ex + "\n Wrong format. Please try again.");
            EndTimer(modify, tasks);
        }
    }
    
    /**
     * 
     * After selection, and validation, deletes a selected {@link Task task}
     * Uses the {@link #selectMe(java.util.List) selectME} method
     */
    private static void deleteTask() {
                
        int monthIndex, dayDate, selector = 0;
        String ID;
        List<WorkDay> days;
        WorkDay selected;
        Task taskToDelete;
        
        System.out.println("Please type the idex of the month you want to delete a task from:");
        monthList(); 
        
        monthIndex = makeItInteger();        
        while (monthIndex > timeLogger.getMonths().size() || monthIndex == 0){
            System.out.println("Invalid month id. Please select a valid id from the list of months.");
            monthIndex = makeItInteger();
        }
        
        System.out.println("Please type the date of the day you want to delete a task from:, with 2 digits."
                + "Example: 02");
        days = timeLogger.getMonths().get(monthIndex - 1).getDays();
        days.forEach((day) -> {
            System.out.print(day.getActualDay().format(DateTimeFormatter.ofPattern("MM.dd")) + ", ");
        });
        System.out.println("");
        dayDate = makeItInteger();
        selected = giveMeADay(days, dayDate);
        
        System.out.println("These are the tasks for the selected day:");
        for (Task task : selected.getTasks()) {
                selector++;
                System.out.println(selector + ". " + task.getTaskId() + " " + task.getStartTime());
        }        
        System.out.println("Please select the task you want to delete:");
        taskToDelete = selectMe(selected.getTasks());
        
        System.out.println("The task you chose to delete is: " + taskToDelete.getTaskId()
                + " " + taskToDelete.getComment());
        System.out.println("If you are 100% sure, that you want to delete it, please type in the ID of the task."
                + "\nIf you enter anything else, the application will return to the main menu.");
        ID = consoleReader();
        if (ID.equals(taskToDelete.getTaskId())){
            System.out.println("Deleting the chosen object.......");
            for (int i = 0; i < selected.getTasks().size(); i++){
                if (selected.getTasks().get(i).equals(taskToDelete)){
                    selected.getTasks().remove(i);
                    taskToDelete = null;
                }
            }
            System.out.println("Sucess.");            
            backToMainMenu();
        }
        else{
            backToMainMenu();
        }
    }

/**
 *  
 * After selection, and validation, deletes a selected {@link Task task}
 * Uses the {@link #selectMe(java.util.List) selectME},
 * {@link #setIt(Entities.Task, java.lang.String) setIt},
 * {@link #setEnd(Entities.Task, java.lang.String) setEnd} methods
 */    
    private static void modifyTask() {
                
        int monthIndex, dayDate, selector = 0;
        String ID, input;
        List<WorkDay> days;
        WorkDay selected;
        Task taskToModify;
        
        System.out.println("Please type the idex of the month you want to modify a task at:");
        monthList(); 
        
        monthIndex = makeItInteger();        
        while (monthIndex > timeLogger.getMonths().size() || monthIndex == 0){
            System.out.println("Invalid month id. Please select a valid id from the list of months.");
            monthIndex = makeItInteger();
        }
        
        System.out.println("Please type the date of the day you want to modify a task at:, with 2 digits."
                + "Example: 02");
        days = timeLogger.getMonths().get(monthIndex - 1).getDays();
        days.forEach((day) -> {
            System.out.print(day.getActualDay().format(DateTimeFormatter.ofPattern("MM.dd")) + ", ");
        });
        System.out.println("");
        dayDate = makeItInteger();
        selected = giveMeADay(days, dayDate);
        
        System.out.println("These are the tasks for the selected day:");
        for (Task task : selected.getTasks()) {
                selector++;
                System.out.println(selector + ". " + task.getTaskId() + " " + task.getStartTime() + " - " 
                        + task.getEndTime());
        }        
        System.out.println("Please select the task you want to modify:");
        taskToModify = selectMe(selected.getTasks());
        
        System.out.println("The task you chose to modify is: " + taskToModify.getTaskId());
        System.out.println("To modify a value, enter the new value  after the current one is displayed."
                + "If you type an empty space, the value will be unchanged.");
        System.out.println("Start Time {" + taskToModify.getStartTime() + "}");
        input = consoleReader();
        if (!input.equals(" ")){
            setIt(taskToModify, input);
        }
        System.out.println("End Time {" + taskToModify.getEndTime() + "}");
        input = consoleReader();
        if (!input.equals(" ")){
            setEnd(taskToModify, input);
        }
        System.out.println("Comment {" + taskToModify.getComment()+ "}");
        input = consoleReader();
        if (!input.equals(" ")){
            taskToModify.setComment(input);
        }
        
        System.out.println("After modifying it the task become something else, someone else."
                + "\n" + taskToModify.getTaskId() + "{" + taskToModify.getStartTime() + "}" + " - " +
                 "{" + taskToModify.getEndTime()+ "}" + "{" + taskToModify.getComment()+ "}");
        
        backToMainMenu();        
    }
    
    /**
     * 
     * Sets the endTime of the given {@link Task task}
     * @param task : {@link Task task}
     * @param startTime : {@link String string} read from the console
     */
    private static void setEnd(Task task, String endTime){
        try{
            task.setEndTime(endTime);
        }
        catch(Exception ex){
            System.out.println(ex + "\n Wrong format. Please try again.");
            setEnd(task, consoleReader());
        }
    }    

    /**
     * 
     * Prints out the statisctics of the chosen {@link WorkMonth month}, first the sum of the month,
     * then {@link WorkDay day} by day 
     */
    private static void statistics() {
        
        int monthIndex;
        List<WorkDay> days;
        
        System.out.println("Please type the idex of the month you want to inspect.");
        monthList();   
        monthIndex = makeItInteger();
        while (monthIndex == 0 || monthIndex > timeLogger.getMonths().size()){
            System.out.println("Invalid month id. Please select a valid id from the list of months.");
            monthIndex = makeItInteger();            
        }        
        
        System.out.println("-----" + timeLogger.getMonths().get(monthIndex - 1).getDate() + "------");
        System.out.println("Days worked in the month : " + 
                timeLogger.getMonths().get(monthIndex - 1).getDays().size());
        System.out.println("Required workhours of the month: " + 
                timeLogger.getMonths().get(monthIndex - 1).getRequiredMinPerMonth() / 60);
        System.out.println("Hours worked in the month: " + 
                timeLogger.getMonths().get(monthIndex - 1).getSumPerMonth() / 60);
        System.out.println("Extra hours worked in the month: " + 
                timeLogger.getMonths().get(monthIndex - 1).getExtraMinPerMonth() / 60);       
        
        days = timeLogger.getMonths().get(monthIndex - 1).getDays();
        days.forEach((day) -> {
            System.out.println("-------------------");
            System.out.println(day.getActualDay().getMonth() + " " + day.getActualDay().getDayOfMonth() );
            System.out.println(day.getActualDay().getDayOfWeek());
            System.out.println("Number of tasks this day: " + day.getTasks().size());
            System.out.println("Required work hours this day: " + day.getRequiredMinPerDay() / 60);
            System.out.println("Workhours this day: " + day.getSumPerDay() / 60);
            System.out.println("Extra hours worked this day: " + day.getExtraMinPerDay() / 60);
            System.out.println("End time of the last task: " + day.endTimeOfTheLastTask());
        });
        System.out.println("-------------------");
        
        backToMainMenu();        
    }
}