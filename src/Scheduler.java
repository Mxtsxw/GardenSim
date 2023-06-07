public class Scheduler implements Runnable{
    Model model;

    public Scheduler(Model m){
        this.model = m;
    }

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(this.model.getCooldown());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Clock");
        }
    }
}
