package davletshina.jcourse;
import java.awt.*;
import edu.princeton.cs.algs4.StdDraw;

public class LifeApp {
    int CanvasSize = 100;
    int CellRadius = 5;
    int FieldSize = CanvasSize*CellRadius;
    boolean[][] currentGeneration = new boolean[CanvasSize][CanvasSize];
    boolean[][] nextGeneration = new boolean[CanvasSize][CanvasSize];

    public static void main(String[] args) {
        new LifeApp().run();
    }

    public void run() {
        // init canvas
        StdDraw.setCanvasSize(FieldSize, FieldSize);
        StdDraw.setXscale(0, FieldSize);
        StdDraw.setYscale(FieldSize, 0);
        // clear
        StdDraw.clear(Color.WHITE);

        // создание глайдера
        currentGeneration[0][1] = true;
        currentGeneration[1][2] = true;
        currentGeneration[2][0] = true;
        currentGeneration[2][1] = true;
        currentGeneration[2][2] = true;
        draw();

        StdDraw.enableDoubleBuffering();
        // бесконечный цикл
       while (true) {
           StdDraw.clear(Color.WHITE);
           long tStart = System.currentTimeMillis();
           next();
           draw();
           long tFrame = System.currentTimeMillis() - tStart;
           String time = "frame: " + tFrame + "ms";
           String fps = "fps: " + (1000.0 / tFrame);
           StdDraw.textLeft(300, 20, time);
           StdDraw.textLeft(300, 40, fps);

           try {
               Thread.sleep(200);
           }
           catch (InterruptedException e) {
               e.printStackTrace();
           }
           StdDraw.show();
        }
    }

    // подсчет количества соседей клетки
    public int countNeighbors(int x, int y) {
        int count = 0;
        for (int dx = -1; dx < 2; dx++) {
            for (int dy = -1; dy < 2; dy++) {
                int nX = x + dx;
                int nY = y + dy;
                nX = (nX < 0) ? CanvasSize - 1 : nX;
                nY = (nY < 0) ? CanvasSize - 1 : nY;
                nX = (nX > CanvasSize - 1) ? 0 : nX;
                nY = (nY > CanvasSize - 1) ? 0 : nY;
                count += (currentGeneration[nX][nY]) ? 1 : 0;
            }
        }
        if (currentGeneration[x][y]) { count--; }
        return count;
    }

    // расчет нового поколения
    public void next() {
        for (int x = 0; x < CanvasSize; x++) {
            for (int y = 0; y < CanvasSize; y++) {
                int count = countNeighbors(x, y);
                nextGeneration[x][y] = currentGeneration[x][y];
                nextGeneration[x][y] = (count == 3) || nextGeneration[x][y];
                nextGeneration[x][y] = ((count >= 2) && (count <= 3)) && nextGeneration[x][y];
            }
        }
        for (int x = 0; x < CanvasSize; x++) {
            System.arraycopy(nextGeneration[x], 0, currentGeneration[x], 0, CanvasSize);
        }
    }

    // отрисовка клетки
        public void draw() {
            for (int x = 0; x < CanvasSize; x++) {
                for (int y = 0; y < CanvasSize; y++) {
                    if (currentGeneration[x][y]) {
                        StdDraw.setPenColor(Color.BLACK);
                        StdDraw.filledCircle(x*2*CellRadius+CellRadius, y*2*CellRadius+CellRadius, CellRadius);
                    }
                }
            }
        }
}
