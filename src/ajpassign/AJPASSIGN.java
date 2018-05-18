/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajpassign;

import java.io.*;
import java.net.URL;
import java.util.*;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author chuny
 */
public class AJPASSIGN {

    /**
     * @param args the command line arguments
     */
    public static HashMap<String, String> num = new LinkedHashMap<String, String>();// key is cc11 etc
    public static HashMap<String, ArrayList<String>> desc = new LinkedHashMap<String, ArrayList<String>>();// key is name 

    public static void main(String[] args) throws IOException {
        String file = "src/MRT.txt";
        ArrayList<String> mrt = new ArrayList<String>();
        ArrayList<String> temp = new ArrayList<String>();

        mrt = file(file);
        String[] lines = {"NS", "EW", "CG", "DT", "CC"};
        String pattern = "^.*[a-zA-Z]{2}[0-9].*$";
        int o = 0;
        String k = "";
        String v = "";
        ArrayList<ArrayList<String>> lok;

        for (String i : mrt) {
            String st = "(start)";
            String en = "(end)";
            if (!(i.contains(st) || i.contains(en))) {
                if (o == 0) {
                    k = i;
                    o = o + 1;
                } else if (o == 1) {
                    v = i;

                    //System.out.println(k+" "+v);
                    ArrayList<String> l = new ArrayList<String>();
                    //System.out.println(v);
                    l = desc.get(v);

                    //System.out.println(l);
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
        while (what != 3) {
            try {
                what = 3;
                what = Integer.parseInt(JOptionPane.showInputDialog("1. Search by station\n2. Search for route\n3. Quit"));
            } catch (Exception e) {
            }
            if (what == 2) {
//try{
                String message = "";
                String fr = JOptionPane.showInputDialog("from where");
                String too = JOptionPane.showInputDialog("to where");
                ArrayList<String> ff = new ArrayList<String>();
                ArrayList<String> tt = new ArrayList<String>();
                String from1 = "";
                String to1 = "";

                if (!(fr.matches(pattern)) || !(too.matches(pattern))) {
                    if (!(fr.matches(pattern))) {
                        ff = desc.get(fr);
                    }
                    if (!(too.matches(pattern))) {
                        tt = desc.get(too);
                    }
                } else {
                    ff.add(fr);
                    tt.add(too);
                }

                for (String from : ff) {
                    for (String to : tt) {

                        from1 = from.replaceAll("\\d", "");//eg. NS
                        to1 = to.replaceAll("\\d", "");//eg. CC
                        String[] myStringArray = lines;

                        for (int i = 0; i < myStringArray.length; i++) {
                            if (myStringArray[i].equals(from1) || myStringArray[i].equals(to1)) {
                                myStringArray[i] = null;

                            }
                            
                            
                        }

                        ArrayList<String> loc = new ArrayList<String>();
                        
                        

                        String gf = num.get(from);
                       
                        String gt = num.get(to);
                       
                        ArrayList<String[]> midd = new ArrayList<String[]>();
                        if (gf == null || gt == null) {
                            JOptionPane.showMessageDialog(null, "invalid station code!");
                        } else if (!(from1.equals(to1))) {

                            ArrayList<String> p = search(from1);
                            ArrayList<String> pp = search(to1);
                            //consider making route class
                            p = splitting(p);
                            //System.out.println(p);
                            pp = splitting(pp);
                            //System.out.println(pp);
                            for (String i : con(p, pp)) {
                                String[] ppppp = {i};
                                midd.add(ppppp);
                            }
                            for (String i : myStringArray) {//3 change
                                if (i != null) {
                                    //System.out.println(i + " 1");
                                    ArrayList<String> li = splitting(search(i));

                                    //System.out.println(li);
                                    ArrayList<String> z = con(li, p);
                                    //System.out.println("com " + z);
                                    for (String iiii : z) {

                                        ArrayList<String> lii = splitting(search(i));
                                        
if(li.equals(pp)){

}
                                        //System.out.println(lii);
                                        z = con(li, pp);
                                        
                                        
                                        
                                        //System.out.println("comM " + z);
                                        //System.out.println(pp);
                                        for (String iii : z) {
                                            // System.out.println(iii + "3");
System.out.println(iii+iiii+"www");
                                            if (iii.equals(iiii)||iiii.equals(to)) {//
                                            } else {
                                                String[] n = {iiii, iii};
                                                midd.add(n);
                                            }
                                            //System.out.println(ii + iii);\

                                        }

                                    }
                                } else {
                                }

                            }
Set<String[]> middd = new LinkedHashSet<String[]>(midd);
                            for (String[] i : middd) {
                                loc.clear();

                                loc.add(from);

                                for (String kk : i) {
                                    loc.add(kk);
                                }

                                loc.add(to);
                                System.out.println(loc);
                                int x = num(loc);
                                System.out.println(x);
                                prints(loc, desc);
                            }
                        } else {
                            loc.add(from);
                            loc.add(to);
                            System.out.println(loc);
                            int x = num(loc);
                            System.out.println(x);
                            prints(loc, desc);
                        }
                    }
                }/*
}catch(Exception e){
JOptionPane.showMessageDialog(null, e.toString());
}*/
            } else if (what == 1) {
                String wher = JOptionPane.showInputDialog("Station num");
                if (!(wher.matches(pattern))) {

                    ArrayList<String> choice = desc.get(wher);

                    wher = drop(choice);

                }
                String where = wher.replaceAll("\\d", "");
                ArrayList<String> p = search(where);

                String pr = "";
                
                if (p.equals("") || num.get(wher) == null) {
                    pr = "invalid station code";
                } else {
                    String kk = "";
                    String kkk = tries(wher);
                    for (String i : p) {
                        if (i.equals(kkk)) {
                            kk = kk + i + "   <--You are here" + "\n";
                        } else {
                            kk = kk + i + "\n";
                        }
                    }
                    pr = kk;

                }
                String url = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/dd/Achtung.svg/220px-Achtung.svg.png";
                if (where.equals("NS")) {
                    url = "http://1.bp.blogspot.com/-_gBdZBmmSqc/UgjhdpGZpII/AAAAAAAAFIw/dqHkbiusDHk/s1600/mrt_northsouthline.gif";
                } else if (where.equals("CC")) {
                    url = "http://2.bp.blogspot.com/-njFh6mpkSOA/To_PbLeRpRI/AAAAAAAAAdI/AQl5mPg0XyA/s1600/mrt_circleline.gif";
                } else if (where.equals("DT")) {
                    url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTI5j-3bcEcwgIqBTAdEtwiiCsyjPgupJi0yqN4chD_BV4qxEbb";
                } else if (where.equals("EW")) {
                    url = "http://2.bp.blogspot.com/-QI8fqDQN_fY/UgjhSXlywRI/AAAAAAAAFIo/hIob0Md8Nl0/s1600/mrt_eastwestline.gif";
                } else if (where.equals("CG")) {

                }

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
        BufferedReader reader = new BufferedReader(new FileReader(filename));
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
                mrt.add(m.getKey() + " , " + m.getValue());
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
        return p1;
    }

    static void prints(ArrayList<String> l, HashMap<String, ArrayList<String>> desc) {
        int s = l.size();
        int i = 0;
        String k = "";
        while (i < s) {
            if (i == 0) {
                k = k + "From : " + full(l.get(i), desc) + "\n";
            } else if (i == (s - 1)) {
                k = k + "End At : " + full(l.get(i), desc) + "\n";
            } else {
                k = k + "Change At : " + full(l.get(i), desc) + "\n";
            }
            i = i + 1;
        }
        //System.out.println(k);
        JOptionPane.showMessageDialog(null, k);
    }

    static String tries(String a) {
        String k = a + " , " + num.get(a);

        return k;
    }

    static String find(String a) {
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

    static String full(String a, HashMap<String, ArrayList<String>> d) {
        String k = "";
        String pattern = "^.*[a-zA-Z]{2}[0-9].*$";
        if (a.matches(pattern)) {
            k = find(a) + " " + a;
        } else {
            k = a + " " + d.get(a);
        }

        return k;
    }

    static String in(String a, HashMap<String, ArrayList<String>> d) {
        String k = "";

        String pattern = "^.*[a-zA-Z]{2}[0-9].*$";
        if (a.matches(pattern)) {
            k = a;
        } else {
            k = d.get(a).get(0);
        }

        return k;
    }

    static String drop(ArrayList<String> choice) {
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

    static ArrayList<String> con(ArrayList<String> p, ArrayList<String> pp) {
        ArrayList<String> commonn = new ArrayList<String>(p);
        commonn.retainAll(pp);
        //System.out.println(commonn);
        return commonn;
    }

    static int num(ArrayList<String> z) {
        ArrayList<String> jjj = new ArrayList<String>();
        ArrayList<ArrayList<String>> jj = new ArrayList<ArrayList<String>>();
        ArrayList<String> kkkk = new ArrayList<String>();
        for (String i : z) {
            String pattern = "^.*[a-zA-Z]{2}[0-9].*$";
            if (i.matches(pattern)) {
                kkkk = new ArrayList<String>();
                System.out.println(kkkk);
                kkkk.add(i);
                System.out.println(kkkk);
                jj.add(kkkk);

            } else {
                jjj = desc.get(i);

                jj.add(jjj);
            }
        }

        System.out.println(jj + "podnd");

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
                //System.out.println(gt(c.get(k),i)gt(c.get(k - 1), m));
                String pattern = "^.*[a-zA-Z]{2}[0-9].*$";
                System.out.println(item);
                System.out.println(i+"bb"+m);
                for (String h : i) {
                    item.add(h.replaceAll("\\d", ""));
                }
                for (String h : m) {
                    mtem.add(h.replaceAll("\\d", ""));
                }
                ArrayList<String> com = con(item, mtem);
                System.out.println(item+"aa"+mtem);
                System.out.println(com+"qqqqq");
                String ii = "";
                String mm = "";

                String first = com.get(0);
                ArrayList<String> li = splitting(search(first.replaceAll("\\d", "")));

               
               
               
               
                
               
               
               
               
               
                System.out.println(li+"heh");
                System.out.println(m+"qq"+i);
                int ss =  gt(li, num.get(m.get(0)))-gt(li, num.get(i.get(0)));
                n = n+(ss < 0 ? -ss : ss);
                m = i;
                
            }
            //find diff between i and m

            k = k + 1;

        }
        System.out.println(n);
        return n;
    }

    static int gt(ArrayList<String> mArrayList, String item) {
        int i = 0;
        System.out.println(mArrayList);
        ////System.out.println(item + " in gt");
        for (i = 0; i < mArrayList.size(); i++) {
            //System.out.println(mArrayList.get(i)+item);
            if (mArrayList.get(i).equalsIgnoreCase(item)) {
                //System.out.println(i);
                return i;
            }
        }
        return 1000;
    }
}
