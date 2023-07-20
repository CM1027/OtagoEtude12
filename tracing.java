import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.HashSet;
import java.text.DecimalFormat;

public class tracing extends Thread {
  public static ArrayList<String> people = new ArrayList<String>();
  public static ArrayList<ArrayList<String>> peopleLists = new ArrayList<ArrayList<String>>();
  public static ArrayList<ArrayList<Integer>> timeList = new ArrayList<ArrayList<Integer>>();
  public static ArrayList<Integer> time = new ArrayList<Integer>();
  public static ArrayList<String> allContacts = new ArrayList<String>(); // a list of all contacts. 
  public static ArrayList<String> contactList = new ArrayList<String>(); // contains all starting threats
  public static ArrayList<String> contactThread1 = new ArrayList<String>(); // every contact thread 1 finds
  public static ArrayList<String> contactThread2 = new ArrayList<String>(); // every contact thread 2 finds
  public static ArrayList<String> contactThread3 = new ArrayList<String>(); // every contact thread 3 finds
  public static ArrayList<String> contactThread4 = new ArrayList<String>(); // every contact thread 4 finds
  public static ArrayList<Integer> timeObjective = new ArrayList<Integer>(); // contains all starting time objectives
  public static HashMap<String, Integer> magicThreats = new HashMap<String, Integer>(); // contains all magic threat info
  public static HashMap<String, Integer> gtThreats = new HashMap<String, Integer>();
  public static int threadNumber;
  public static String commandType;
  
  private class RunnableImpl implements Runnable {
    
    /** constructor */ 
    public RunnableImpl(ArrayList<String> peopleIn, ArrayList<Integer> timeIn, int threadNumberIn, String commandTypeIn) {
      threadNumber = threadNumberIn;
      commandType = commandTypeIn;
      peopleLists.add(threadNumber - 1, peopleIn);
      timeList.add(threadNumber - 1, timeIn);
    }
    
    /** runs a thread */
    public void run() {
      tracing tr = new tracing();
      if (threadNumber == 1) {
        threadNumber--;
        if (commandType.equals("pt")) {
        contactThread1 = tr.findTargets(peopleLists.get(threadNumber), timeList.get(threadNumber), timeObjective.get(0), contactList);
        } else if (commandType.equals("gt")) {
          contactThread1 = tr.findTargetsTimes(peopleLists.get(threadNumber), timeList.get(threadNumber), timeObjective.get(0), gtThreats);
        }
      } else if (threadNumber == 2) {
        threadNumber--;
        if (commandType.equals("pt")) {
          contactThread2 = tr.findTargets(peopleLists.get(threadNumber), timeList.get(threadNumber), timeObjective.get(0), contactList);
        } else if (commandType.equals("gt")) {
          contactThread2 = tr.findTargetsTimes(peopleLists.get(threadNumber), timeList.get(threadNumber), timeObjective.get(0), gtThreats);
        }
      } else if (threadNumber == 3) {
        threadNumber--;
        if (commandType.equals("pt")) {
          contactThread3= tr.findTargets(peopleLists.get(threadNumber), timeList.get(threadNumber), timeObjective.get(0), contactList);
        } else if (commandType.equals("gt")) {
          contactThread3 = tr.findTargetsTimes(peopleLists.get(threadNumber), timeList.get(threadNumber), timeObjective.get(0), gtThreats);
        }
      } else if (threadNumber == 4) {
        threadNumber--;
        if (commandType.equals("pt")) {
          contactThread4 = tr.findTargets(peopleLists.get(threadNumber), timeList.get(threadNumber), timeObjective.get(0), contactList);
        } else if (commandType.equals("gt")) {
          contactThread4 = tr.findTargetsTimes(peopleLists.get(threadNumber), timeList.get(threadNumber), timeObjective.get(0), gtThreats);
        }
      }
    } 
  }
  
  public static void main(String[] args) throws IOException {
    if (!people.isEmpty()) { // since it is a global variable, it needs to be reset aside from hitting "Compile"
      people.clear();
      contactList.clear();
      timeObjective.clear();
    }
    ArrayList<String> times = new ArrayList<String>(); 
    ArrayList<Integer> timeContacts = new ArrayList<Integer>();
    Scanner scan = new Scanner(new File(args[0]));
    
    tracing t = new tracing();
    
    int cycles = 0;
    while (scan.hasNext()) {
      String s = scan.next(); // add first person
      s += " " + scan.next(); // add second person
      people.add(s); // add both people to the arraylist
      times.add(scan.next()); // add the time
      cycles++; // the line's done
      time = t.toInt(times); // passing in the times still useful
    }
    HashMap<Integer, String> contactMap = new HashMap<Integer, String>();
    
    for (int i = 0; i < people.size(); i++) {
      contactMap.put(time.get(i), people.get(i));
    }
    
    ArrayList<String> tempPeopleHalf1 = new ArrayList<String>();
    ArrayList<Integer> tempTimeHalf1 = new ArrayList<Integer>();
    ArrayList<String> tempPeopleHalf2 = new ArrayList<String>();
    ArrayList<Integer> tempTimeHalf2 = new ArrayList<Integer>();
    for (int b = 0; b < people.size() / 2; b++) {
      tempPeopleHalf1.add(people.get(b));
      tempTimeHalf1.add(time.get(b));
    }
    for (int y = people.size() / 2; y < people.size(); y++) {
      tempPeopleHalf2.add(people.get(y));
      tempTimeHalf2.add(time.get(y)); 
    }
    ArrayList<String> tempPeople1 = new ArrayList<String>();
    ArrayList<Integer> tempTime1 = new ArrayList<Integer>();
    ArrayList<String> tempPeople2 = new ArrayList<String>();
    ArrayList<Integer> tempTime2 = new ArrayList<Integer>();
    ArrayList<String> tempPeople3 = new ArrayList<String>();
    ArrayList<String> tempPeople4 = new ArrayList<String>();
    ArrayList<Integer> tempTime3 = new ArrayList<Integer>();
    ArrayList<Integer> tempTime4 = new ArrayList<Integer>();
    for (int b = 0; b < people.size() / 4; b++) {
      tempPeople1.add(people.get(b));
      tempTime1.add(time.get(b));
    }
    for (int y = people.size()/4; y < people.size() /2; y++) {
      tempPeople2.add(people.get(y));
      tempTime2.add(time.get(y)); 
    }
    for (int k = people.size()/2; k < (people.size() / 2 + people.size()/4); k++) {
      tempPeople3.add(people.get(k));
      tempTime3.add(time.get(k));
    }
    for (int j = people.size()/2 + people.size()/4; j < people.size(); j++) {
      tempPeople4.add(people.get(j));
      tempTime4.add(time.get(j));
    }
    System.out.println("Please insert your command in the form");
    System.out.println("type of search, person id, then time, then thread number (1, 2, or 4). With a space between each integer");
    Scanner in = new Scanner(System.in);
    while(in.hasNextLine()){
      if (!contactList.isEmpty()) { // resets all for every command, since we're hunting new people every time
        contactList.clear();
        timeObjective.clear();
        magicThreats.clear();
        allContacts.clear();
        peopleLists.clear();
        timeList.clear();
      }
      String strIn = in.nextLine();
      String[] sa = strIn.split(" ");
      String commandType = "";
      commandType = sa[0];
      if (commandType.equals("pt")) { // one person
        contactList.add(sa[1]);
        timeObjective.add(Integer.parseInt(sa[sa.length-2]));
        int threadNo = Integer.parseInt(sa[sa.length - 1]);
        Thread t1 = new Thread(t.new RunnableImpl(tempPeople1, tempTime1, 1, commandType));
        Thread t2 = new Thread(t.new RunnableImpl(tempPeople2, tempTime2, 2, commandType));
        Thread t3 = new Thread(t.new RunnableImpl(tempPeople3, tempTime3, 3, commandType));
        Thread t4 = new Thread(t.new RunnableImpl(tempPeople4, tempTime4, 4, commandType));
        if (threadNo == 2) {
          long startTime = System.nanoTime();
          Thread threadHalf1 = new Thread(t.new RunnableImpl(tempPeopleHalf1, tempTimeHalf1, 1, commandType));
          Thread threadHalf2 = new Thread(t.new RunnableImpl(tempPeopleHalf2, tempTimeHalf2, 2, commandType));
          threadHalf1.start();
          threadHalf2.start();
          try {
            threadHalf1.join();
            threadHalf2.join();
          } catch (Exception e) {
            System.out.println("EXCEPTION" + e);
          } 
          allContacts.addAll(contactThread1);
          allContacts.addAll(contactThread2);
          long endTime = System.nanoTime();
          long totalTime = endTime - startTime;
          System.out.println("TWO THREADS TIME (in ns) " + totalTime);
          ArrayList<String> finalContacts = removeDuplicates(allContacts);
          System.out.println(finalContacts);
        } else if (threadNo == 1) {
          long startTimeSingle = System.nanoTime();
          allContacts = t.findTargets(people, time, timeObjective.get(0), contactList);
          ArrayList<String> finalContactsSingle = removeDuplicates(allContacts);
          System.out.println(finalContactsSingle);
          long endTimeSingle = System.nanoTime();
          long totalTimeSingle = endTimeSingle - startTimeSingle;
          System.out.println("SINGLE THREADING TIME (in ns) " + totalTimeSingle);
        } else if (threadNo == 4) {
          long startTime = System.nanoTime();
          t1.start();
          t2.start();
          t3.start();
          t4.start();
          try {
            t1.join();
            t2.join();
            t3.join();
            t4.join(); 
          } catch (Exception e) {
            System.out.println(e);
          } 
          allContacts.addAll(contactThread1);
          allContacts.addAll(contactThread2);
          allContacts.addAll(contactThread3);
          allContacts.addAll(contactThread4);
          long endTime = System.nanoTime();
          long totalTime = endTime - startTime;
          System.out.println("FOUR THREADS TIME (in ns) " + totalTime);
          ArrayList<String> finalContacts = removeDuplicates(allContacts);
          System.out.println(finalContacts);
        }
      } else if (commandType.equals("gt")) { // group of contacts
        String gtListIn = "";
        for (int arg = 1; arg < sa.length; arg++) {
          gtListIn = gtListIn + sa[arg] + " ";
        }
        String[] persons = gtListIn.split(" ");
        int allInfoIn = 0; 
        String s = "";
        for (int l = 0; l < persons.length; l++) {
          if (allInfoIn < 2) {
            s += persons[l] + " ";
            allInfoIn++;
          } else {
            String[] info = s.split(" ");
            gtThreats.put(info[0], Integer.parseInt(info[1]));
            allInfoIn = 0;
            s = "";
          }
        }
        Thread t1 = new Thread(t.new RunnableImpl(tempPeople1, tempTime1, 1, commandType));
        Thread t2 = new Thread(t.new RunnableImpl(tempPeople2, tempTime2, 2, commandType));
        Thread t3 = new Thread(t.new RunnableImpl(tempPeople3, tempTime3, 3, commandType));
        Thread t4 = new Thread(t.new RunnableImpl(tempPeople4, tempTime4, 4, commandType));
        timeObjective.add(Integer.parseInt(sa[sa.length - 2]));
        int threadNo = Integer.parseInt(sa[sa.length - 1]);
        if (threadNo == 2) {
          long startTime = System.nanoTime();
          Thread threadHalf1 = new Thread(t.new RunnableImpl(tempPeopleHalf1, tempTimeHalf1, 1, commandType));
          Thread threadHalf2 = new Thread(t.new RunnableImpl(tempPeopleHalf2, tempTimeHalf2, 2, commandType));
          threadHalf1.start();
          threadHalf2.start();
          try {
            threadHalf1.join();
            threadHalf2.join(); 
          } catch (Exception e) {
            System.out.println(e);
          }
          allContacts.addAll(contactThread1);
          allContacts.addAll(contactThread2);
          long endTime = System.nanoTime();
          long totalTime = endTime - startTime;
          System.out.println("TWO THREADS TIME (in ns) " + totalTime);
          ArrayList<String> finalContacts = removeDuplicates(allContacts);
          System.out.println(finalContacts);
        } else if (threadNo == 4) {
          long startTime = System.nanoTime();
          t1.start();
          t2.start();
          t3.start();
          t4.start();
          try {
            t1.join();
            t2.join();
            t3.join();
            t4.join(); 
          } catch (Exception e) {
            System.out.println(e);
          } 
          allContacts.addAll(contactThread1);
          allContacts.addAll(contactThread2);
          allContacts.addAll(contactThread3);
          allContacts.addAll(contactThread4);
          long endTime = System.nanoTime();
          long totalTime = endTime - startTime;
          System.out.println("FOUR THREADS TIME (in ns) " + totalTime);
          ArrayList<String> finalContacts = removeDuplicates(allContacts);
          System.out.println(finalContacts);
        } else if (threadNo == 1) {
          long startTimeSingle = System.nanoTime();
          allContacts = t.findTargetsTimes(people, time, timeObjective.get(0), gtThreats);
          ArrayList<String> finalContactsSingle = removeDuplicates(allContacts);
          System.out.println(finalContactsSingle);
          long endTimeSingle = System.nanoTime();
          long totalTimeSingle = endTimeSingle - startTimeSingle;
          System.out.println(totalTimeSingle);
        }
      } else if (commandType.equals("mt")) { // magic
        String magicListIn = "";
        for (int arg = 1; arg < sa.length; arg++) {
          magicListIn = magicListIn + sa[arg] + " ";
        }
        String[] persons = magicListIn.split(" ");
        int allInfoIn = 0; 
        String s = "";
        for (int l = 0; l < persons.length; l++) {
          if (allInfoIn < 2) {
            s += persons[l] + " ";
            allInfoIn++;
          } else {
            String[] info = s.split(" ");
            magicThreats.put(info[0], Integer.parseInt(info[1]));
            allInfoIn = 0;
            s = "";
          }
          if (l == persons.length-1) {
            System.out.println(s);
            String[] info = s.split(" ");
            magicThreats.put(info[0], Integer.parseInt(persons[l]));
            allInfoIn = 0;
            s = "";
            break;
          }
        }
        DecimalFormat df = new DecimalFormat("###.###");
        HashMap<String, Double> magicLikelihood = new HashMap<String, Double>();
        magicLikelihood = t.magicFinder(people, time, magicThreats);
        for (Map.Entry<String, Double> entry : magicLikelihood.entrySet()) { // unsure if we need to sort output
          String key = entry.getKey();
          Double value = entry.getValue();
          System.out.println("Person: " + key);
          System.out.println("Likelihood of person having powers " + df.format(value)); 
        }
      } else if (commandType.equals("st")) {
        System.out.println("You have stopped the program.");
        break;
      }
    }
  } // END
  
  public HashMap<String, Double> magicFinder(ArrayList<String> contacts, ArrayList<Integer> times, HashMap<String, Integer> magic) {
    // Hashmap <KEY, VALUE>
    ArrayList<String> immediateContact = new ArrayList<String>(); // all the folk in HashMap magic
    HashMap<String, Integer> contactOfContact = new HashMap<String, Integer>(); // separated by one degree
    HashMap<String, Integer> twoRemoved = new HashMap<String, Integer>(); // separated from a person in magic by two
    HashMap<String, Double> magicLikelihood = new HashMap<String, Double>(); // name, count attached to them
    for (int i = 0; i < contacts.size(); i++) {
      String s = contacts.get(i);
      String[] sa = s.split(" ");
      for (int a = 0; a < sa.length; a++) {
        if (magic.containsKey(sa[a])) {
          if (times.get(i) >= magic.get(sa[a]) && (!magicLikelihood.containsKey(sa[1]) || !magicLikelihood.containsKey(sa[0]))) {
            // if the time is correct and either
            if (a == 0) {
              magicLikelihood.put(sa[1], 0.5); // add to the list a person
              contactOfContact.put(sa[1], times.get(i));
            } else if (a == 1) {
              magicLikelihood.put(sa[0], 0.5); // add the one we already scanned before since it's teamed up with a confirmed danger
              contactOfContact.put(sa[0], times.get(i));
            }
          } if (times.get(i) >= magic.get(sa[a]) && magicLikelihood.containsKey(sa[1])) {
            double count = magicLikelihood.get(sa[1]);
            count+= 0.5;
            magicLikelihood.put(sa[1], count);
          } else if (times.get(i) >= magic.get(sa[a]) && magicLikelihood.containsKey(sa[0])) {
            double count = magicLikelihood.get(sa[0]);
            count+= 0.5;
            magicLikelihood.put(sa[0], count);
          }
        } else if (contactOfContact.containsKey(sa[a])) { // one degree removed...
          if (times.get(i) >= contactOfContact.get(sa[a]) && (!magicLikelihood.containsKey(sa[1]) || !magicLikelihood.containsKey(sa[0]))) {
            if (a == 0) {
              magicLikelihood.put(sa[1], 0.25); // add to the list a person, but add half the value to it
              twoRemoved.put(sa[1], times.get(i));
            } else if (a == 1) {
              magicLikelihood.put(sa[0], 0.25); // add the one we already scanned before since it's teamed up with a confirmed danger
              twoRemoved.put(sa[0], times.get(i));
            }
          }
          if (times.get(i) >= contactOfContact.get(sa[a]) && contactOfContact.containsKey(sa[1])) {
            double count = magicLikelihood.get(sa[1]);
            count = count + (0.25);
            magicLikelihood.put(sa[1], count);
          } else if (times.get(i) >= contactOfContact.get(sa[a]) && contactOfContact.containsKey(sa[0])) {
            double count = magicLikelihood.get(sa[0]);
            count = count + (0.25);
            magicLikelihood.put(sa[0], count);
          }
        } else if (twoRemoved.containsKey(sa[a])) {
          if (times.get(i) >= twoRemoved.get(sa[a]) && (!magicLikelihood.containsKey(sa[1]) || !magicLikelihood.containsKey(sa[0]))) {
            if (a == 0) {
              magicLikelihood.put(sa[1], 0.125); // add to the list a person, but add half the value to it
            } else if (a == 1) {
              magicLikelihood.put(sa[0], 0.125); // add the one we already scanned before since it's teamed up with a confirmed danger
            }
          }
          if (times.get(i) >= twoRemoved.get(sa[a]) && twoRemoved.containsKey(sa[1])) { // maybe need to change magic.get
            double count = magicLikelihood.get(sa[1]);
            count = count + (0.125);
            magicLikelihood.put(sa[1], count);
          } else if (times.get(i) >= twoRemoved.get(sa[a]) && twoRemoved.containsKey(sa[0])) {
            double count = magicLikelihood.get(sa[0]);
            count = count + (0.125);
            magicLikelihood.put(sa[0], count);
          }
        }
      }
    }
    return magicLikelihood;
  }
  
  public static ArrayList<String> removeDuplicates(ArrayList<String> list) {    
    ArrayList<String> newList = new ArrayList<String>();
    for (String word : list) {
      if (!newList.contains(word)) {
        newList.add(word);
      }
    }    
    return newList;
  }
  
    /** This method takes in a number of different targets we need to search for, finds all with attached times, and 
    * returns all potential contacts. */
  public ArrayList<String> findTargetsTimes(ArrayList<String> contacts, ArrayList<Integer> times, int T, HashMap<String, Integer> groupThreats) {
    ArrayList<String> newThreat = new ArrayList<String>();
      for (int i = 0; i < contacts.size(); i++) {
        String s = contacts.get(i);
        String[] sa = s.split(" ");
        for (int a = 0; a < sa.length; a++) {
          if (groupThreats.containsKey(sa[a])) {
            // System.out.println("We've got a match at " + i); // now check if the time at this index is correct
            if (times.get(i) >= groupThreats.get(sa[a]) && (!newThreat.contains(sa[1]) || !newThreat.contains(sa[0]))) {
              if (a == 0) {
                newThreat.add(sa[1]); // add the new potential danger
              } else if (a == 1) {
                newThreat.add(sa[0]); // add the one we already scanned before since it's teamed up with a confirmed danger
              }
            } // end time check
          } // end contacts check
        } // end inner-inner for loop
      } // end inner for loop
    return newThreat;
  } // end findTarget method
  
  
  /** This method takes in a number of different targets we need to search for, finds all with correct times, and 
    * returns all potential contacts. */
  public ArrayList<String> findTargets(ArrayList<String> contacts, ArrayList<Integer> times, int T, ArrayList<String> threats) {
    ArrayList<String> newThreat = new ArrayList<String>();
    for (int t = 0; t < threats.size(); t++) {
      String st = threats.get(t);
      for (int i = 0; i < contacts.size(); i++) {
        String s = contacts.get(i);
        String[] sa = s.split(" ");
        for (int a = 0; a < sa.length; a++) {
          if (sa[a].equals(st)) {
            // System.out.println("We've got a match at " + i); // now check if the time at this index is correct
            if (times.get(i) >= T && (!newThreat.contains(sa[1]) || !newThreat.contains(sa[0]))) {
              if (a == 0) {
                newThreat.add(sa[1]); // add the new potential danger
              } else if (a == 1) {
                newThreat.add(sa[0]); // add the one we already scanned before since it's teamed up with a confirmed danger
              }
            } // end time check
          } // end contacts check
        } // end inner-inner for loop
      } // end inner for loop
    } // end outer for loop
    return newThreat;
  } // end findTarget method
  
  /* shows the output of an ArrayList of Strings */ 
  public void showOutput(ArrayList<String> list) {
    for (int i = 0; i < list.size(); i++) {
      System.out.print(list.get(i) + " ");
    }
    System.out.println();
  }
  
  public ArrayList<Integer> toInt(ArrayList<String> list) {
    ArrayList<Integer> intList = new ArrayList<Integer>();
    for (int i = 0; i < list.size(); i++) {
      intList.add(Integer.parseInt(list.get(i)));
    }
    return intList;
  }
}