import Plants.Plants;

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

            Plants[][] grid = this.model.getGrid();
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid.length; j++) {
                    if (grid[i][j] != null){
                        grid[i][j].updateGerminationState();
                        System.out.println(grid[i][j] + " " + grid[i][j].getGerminationState());
                    } else {
                        System.out.println("null");
                    }
                }
            }

            System.out.println("Clock");
        }
    }
}
