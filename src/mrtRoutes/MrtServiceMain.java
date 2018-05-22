/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mrtRoutes;

import java.io.*;
import java.net.URL;
import java.util.*;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author chuny
 */
public class MrtServiceMain {

    /**
     * @param args the command line arguments
     */
    public static HashMap<String, String> num = new LinkedHashMap<String, String>();// key is cc11 etc
    public static HashMap<String, ArrayList<String>> desc = new LinkedHashMap<String, ArrayList<String>>();// key is name 

    public static void run() throws IOException {
        String file = "src/mrtRoutes/resources/MRT.txt";
        ArrayList<String> mrt = new ArrayList<String>();
        mrt = file(file);
        String pattern = "^.*[a-zA-Z]{2}[0-9].*$";
        int o = 0;
        String k = "";
        String v = "";
        for (String i : mrt) {
            String st = "(start)";
            String en = "(end)";
            if (!(i.contains(st) || i.contains(en))) {
                if (o == 0) {
                    k = i;
                    o = o + 1;
                } else if (o == 1) {
                    v = i;
                    ArrayList<String> l = new ArrayList<String>();
                    l = desc.get(v);
                    if (l == null) {
                        l = new ArrayList<String>();
                    }
                    l.add(k);
                    desc.put(v, l);
                    num.put(k, v);
                    
                    o = 0;
                }
            }

        }
        int what = 0;
        System.out.println(num);
        System.out.println(desc);
        while (what != 3) {
            try {
                what = 5;
                what = Integer.parseInt(JOptionPane.showInputDialog("1. Search by station\n2. Search for route\n3. Quit"));
            } catch (Exception e) {
            }
            if (what == 2) {// for route
                String fr = JOptionPane.showInputDialog("From where");
                String too = JOptionPane.showInputDialog("  To where");
                ArrayList<String> ff = new ArrayList<String>();
                ArrayList<String> tt = new ArrayList<String>();
                String from1 = "";
                String to1 = "";
                if (!(fr.matches(pattern)) || !(too.matches(pattern))) {//if station num given
                    if (!(fr.matches(pattern))) {
                        ff = desc.get(fr);
                    }
                    if (!(too.matches(pattern))) {
                        tt = desc.get(too);
                    }
                } else {
                    ff.add(fr);//incase is an interchange
                    tt.add(too);
                }
                if (ff == null || tt == null) {
                            JOptionPane.showMessageDialog(null, "invalid station code!");
                } else{
                ArrayList<String> loc1 = new ArrayList<String>();
                ArrayList<String> loc = new ArrayList<String>();
                int small = 1000;
                int x = 0;
                for (String from : ff) {
                    for (String to : tt) {
                        from1 = from.replaceAll("\\d", "");//eg. NS
                        to1 = to.replaceAll("\\d", "");//eg. CC
                        String[] myStringArray = {"NS", "EW", "CG", "DT", "CC", "NE"};
                        for (int i = 0; i < myStringArray.length; i++) {
                            if (myStringArray[i].equals(from1) || myStringArray[i].equals(to1)) {
                                myStringArray[i] = null;
                            }
                        }

                        String gf = num.get(from);//desc of from
                        String gt = num.get(to);//desc of to
                        ArrayList<String[]> midd = new ArrayList<String[]>();
                         if (!(from1.equals(to1))) {
                            ArrayList<String> p = search(from1);//get entire line
                            ArrayList<String> pp = search(to1);//get entire line
                            p = splitting(p);//
                            pp = splitting(pp);
                            for (String i : con(p, pp)) {
                                String[] ppppp = {i};
                                midd.add(ppppp);
                            }
                            for (String i : myStringArray) {//for double interchanges
                                if (i != null) {
                                    ArrayList<String> li = splitting(search(i));
                                    ArrayList<String> z = con(li, p);
                                    for (String iiii : z) {
                                        z = con(li, pp);
                                        for (String iii : z) {
                                            if (iii.equals(iiii) || iiii.equals(num.get(to)) || iii.equals(num.get(from))) {//reomve any false statements
                                            } else {
                                                String[] n = {iiii, iii};
                                                midd.add(n);
                                            }
                                        }
                                    }
                                } else {
                                }
                            }
                            Set<String[]> middd = new LinkedHashSet<String[]>(midd);//for handling of transfers
                            for (String[] i : middd) {
                                loc.clear();
                                loc.add(from);
                                for (String kk : i) {
                                    loc.add(kk);
                                }
                                loc.add(to);
                                x = num(loc);
                                if (small > x) {// check if smallest
                                    loc1 = new ArrayList<String>();
                                    for(String h:loc){
                                    loc1.add(h);
                                    }
                                    small=x;    
                                }
                            }
                        } else {
                            loc.clear();
                            loc.add(from);
                            loc.add(to);
                            x = num(loc);
                            loc1 = new ArrayList<String>();
                                    for(String h:loc){//adding in
                                        loc1.add(h);
                                    }
                        }

                    }
                    if (small > x) {// check for least number of stops
                        loc1 = new ArrayList<String>();
                                    for(String h:loc){
                                    loc1.add(h);
                                    }
                        small=x;
                    }
                    prints(loc1, small);
                }}/*
}catch(Exception e){
JOptionPane.showMessageDialog(null, e.toString());
}*/
            } else if (what == 1) {//get entire station line
                String pr = "";
                ArrayList<String> choice= new ArrayList<String>();
                String wher = JOptionPane.showInputDialog("Station num or Station name");
                if (!(wher.matches(pattern))) {// check if desc or ns11 etc.
                    choice = desc.get(wher);
                    System.out.println(choice);
                    if(choice==null){
                    pr = "invalid station code";
                    }else{
                    wher = drop(choice);
                    }
                }
                //System.out.println(wher);
                String where = wher.replaceAll("\\d", "");
                ArrayList<String> p = search(where);
                
                //System.out.println(wher);
                if (choice == null) {
                    pr = "invalid station code";
                } else {
                    String kk = "";
                    String kkk = wher + " , " + num.get(wher);;
                    for (String i : p) {
                        if (i.equals(kkk)) {
                            kk = kk + i + "   <--You are here" + "\n";// prints where on line u are
                        } else {
                            kk = kk + i + "\n";
                        }
                    }
                    pr = kk;

                }
                System.out.println(where);
                String url = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/dd/Achtung.svg/220px-Achtung.svg.png";// prints appropriate line pic
                if (where.equals("NS")) {
                    url = "http://1.bp.blogspot.com/-_gBdZBmmSqc/UgjhdpGZpII/AAAAAAAAFIw/dqHkbiusDHk/s1600/mrt_northsouthline.gif";
                } else if (where.equals("CC")) {
                    url = "http://2.bp.blogspot.com/-njFh6mpkSOA/To_PbLeRpRI/AAAAAAAAAdI/AQl5mPg0XyA/s1600/mrt_circleline.gif";
                } else if (where.equals("DT")) {
                    url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTI5j-3bcEcwgIqBTAdEtwiiCsyjPgupJi0yqN4chD_BV4qxEbb";
                } else if (where.equals("EW")) {
                    url = "http://2.bp.blogspot.com/-QI8fqDQN_fY/UgjhSXlywRI/AAAAAAAAFIo/hIob0Md8Nl0/s1600/mrt_eastwestline.gif";
                } else if (where.equals("CG")) {
                    url = "http://2.bp.blogspot.com/-QI8fqDQN_fY/UgjhSXlywRI/AAAAAAAAFIo/hIob0Md8Nl0/s1600/mrt_eastwestline.gif";
                } else if (where.equals("NE")) {
                    url = "https://benbanpacking.com/wp-content/uploads/2017/11/NEL-MRT.gif";
                }
                System.out.println(url);
                ImageIcon icon = new ImageIcon(new URL(url));
                JOptionPane.showMessageDialog(null, pr, null,
                        JOptionPane.PLAIN_MESSAGE, icon);
            } else if (what != 3) {
                JOptionPane.showMessageDialog(null, "Please enter a valid value");
            }
        }
        JOptionPane.showMessageDialog(null, "GoodBye");
    }

    static ArrayList file(String filename) throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));// open file
        ArrayList<String> tot = new ArrayList<String>();
        String line;
        while ((line = reader.readLine()) != null) {
            tot.add(line.trim());
        }
        reader.close();
        return tot;
    }

    static ArrayList<String> search(String where) {
        ArrayList<String> mrt = new ArrayList<String>();
        int l = 0;
        for (Map.Entry m : num.entrySet()) {
            if ((m.getKey()).toString().contains(where)) {
                mrt.add(m.getKey() + " , " + m.getValue());//used mainly for line return
                l = l + 1;
            }
        }
        return mrt;
    }

    static ArrayList splitting(ArrayList<String> p) {
        ArrayList<String> p1 = new ArrayList<String>();
        for (String i : p) {
            String[] values = i.split(" , ");
            p1.add(values[1]);
        }
        return p1;//return ns11 etc..
    }

    static void prints(ArrayList<String> l, int x) {// prints nicely formatted destination page
        int s = l.size();
        int i = 0;
        String k = "Fastest Route of "+x+" steps :\n";
        while (i < s) {
            if (i == 0) {
                k = k + "From : " + full(l.get(i)) + "\n";
            } else if (i == (s - 1)) {
                k = k + "End At : " + full(l.get(i)) + "\n";
            } else {
                k = k + "Change At : " + full(l.get(i)) + "\n";
            }
            i = i + 1;
        }
        JOptionPane.showMessageDialog(null, k);
    }

    static String find(String a) {//finds a certain station in a line
        ArrayList<String> temp = new ArrayList<String>();
        String k = "";
        int i = 0;
        Iterator it = desc.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            temp = (ArrayList<String>) pair.getValue();
            if (temp.contains(a)) {
                k = (String) pair.getKey();
            }
            //it.remove(); // avoids a ConcurrentModificationException
        }

        return k;
    }

    static String full(String a) {// gets full statement foe a station desc or num 
        String k = "";
        String pattern = "^.*[a-zA-Z]{2}[0-9].*$";
        if (a.matches(pattern)) {
            k = find(a) + " " + a;
        } else {
            k = a + " " + desc.get(a);
        }
        return k;
    }


    static String drop(ArrayList<String> choice) {// drop down table for line viewing
        String input = "";
        if (choice.size() > 1) {
            String[] choices = new String[choice.size()];

            int i = 0;

            choices = choice.toArray(choices);

            input = (String) JOptionPane.showInputDialog(null, "The MRT station you entered is an interchange, please choose the intended line...",
                    "", JOptionPane.QUESTION_MESSAGE, null, // Use
                    // default
                    // icon
                    choices, choices[1]); // Initial choice
        } else {
            for (String i : choice) {
                input = i;
            }
        }
        return input;

    }

    static ArrayList<String> con(ArrayList<String> p, ArrayList<String> pp) {//compares 2 arraylist for similarities, used for transfer
        ArrayList<String> commonn = new ArrayList<String>(p);
        commonn.retainAll(pp);
        return commonn;
    }

    static int num(ArrayList<String> z) {// loops through and format for returning num of stops
        ArrayList<String> jjj = new ArrayList<String>();
        ArrayList<ArrayList<String>> jj = new ArrayList<ArrayList<String>>();
        ArrayList<String> kkkk = new ArrayList<String>();
        for (String i : z) {
            String pattern = "^.*[a-zA-Z]{2}[0-9].*$";
            if (i.matches(pattern)) {
                kkkk = new ArrayList<String>();
                kkkk.add(i);
                jj.add(kkkk);

            } else {
                jjj = desc.get(i);

                jj.add(jjj);
            }
        }

        ArrayList<String> m = new ArrayList<String>();
        ArrayList<String> item = new ArrayList<String>();
        ArrayList<String> mtem = new ArrayList<String>();
        int n = 0;
        int k = 0;
        String lat = "";

        for (ArrayList<String> i : jj) {
            item = new ArrayList<String>();
            mtem = new ArrayList<String>();
            if (k == 0) {
                m = i;
            } else {
                String pattern = "^.*[a-zA-Z]{2}[0-9].*$";
                for (String h : i) {
                    item.add(h.replaceAll("\\d", ""));
                }
                for (String h : m) {
                    mtem.add(h.replaceAll("\\d", ""));
                }
                ArrayList<String> com = con(item, mtem);
                String ii = "";
                String mm = "";

                String first = com.get(0);
                ArrayList<String> li = splitting(search(first.replaceAll("\\d", "")));

                int ss = gt(li, num.get(m.get(0))) - gt(li, num.get(i.get(0)));
                n = n + (ss < 0 ? -ss : ss);
                m = i;

            }
            //find diff between i and m

            k = k + 1;

        }
        return n;
    }

    static int gt(ArrayList<String> mArrayList, String item) {//finds stops between 2 stations
        
        for (int i = 0; i < mArrayList.size(); i++) {
            if (mArrayList.get(i).equalsIgnoreCase(item)) {
               
                return i;
            }
        }
        return 1000;
    }
}
