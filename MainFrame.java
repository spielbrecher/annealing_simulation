import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame implements ActionListener, Runnable{

    JTextArea ta;
    JButton btnOk;

    MainFrame(){
        super("Demo Clipboard");
        setSize(800, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ta = new JTextArea(30, 60);
        add(new JScrollPane(ta), BorderLayout.CENTER);
        btnOk = new JButton("Ok");
        btnOk.addActionListener(this);
        add(btnOk, BorderLayout.SOUTH);

    }

    double countEnergy(double[] decision){
        return Math.pow(decision[0], 2) + Math.pow(decision[1], 2);
    }

    void simulation(){
        double t=1000;
        int step = 0;
        double[] work = {500, -500};
        double[] best = {500, -500};
        do{
            if(++step % 100 == 0)
                t *= 0.99;

            System.arraycopy(best,0,work, 0, work.length);

            work[0] += Math.random() - 0.5;
            work[1] += Math.random() - 0.5;

            double eBest = countEnergy(best);
            double eWork = countEnergy(work);
            if(eWork<eBest){
                System.arraycopy(work,0,best, 0, work.length);
            }else{
                double p = Math.exp( (eBest - eWork) / t );
                if(Math.random()<p){
                    System.arraycopy(work,0,best, 0, work.length);
                }
            }
            ta.append("Step "+step+" x = "+best[0]+" y = "+best[1]+"\n");
        }while(t>1);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        // copy to clipboard
        //ta.copy();
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        simulation();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            new MainFrame().setVisible(true);
        });
    }

}
