package dev.lpa;

class ChildThread extends Thread {

  @Override
  public void run() {
    for (int i = 0; i < 10; i++) {
      try {
        if (i % 2 == 0)
          System.out.print(" " + i);
        Thread.sleep(500);
      } catch (InterruptedException e) {
        System.out.println("\n" + this.getName() + " got interrupted. ");
        Thread.currentThread().interrupt();
        return;
      }
    }
  }
}

public class Main {

  public static void main(String[] args) {

    Thread thread1 = new ChildThread();

    Runnable myRunnable = () -> {
      for (int i = 0; i < 10; i++) {
        if (i % 2 == 1)
          System.out.print(" " + i);
        try {
          Thread.sleep(500);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    };
    Thread thread2 = new Thread(myRunnable);

    thread1.start();
    thread2.start();

    long now = System.currentTimeMillis();
    try {
      while (thread1.isAlive()) {
        Thread.sleep(200);
        if (System.currentTimeMillis() - now > 2000)
        {
          thread1.interrupt();
          break;
        }
      }

      System.out.println("\nJoin threads.");
//      thread1.join();
      thread2.join();

      if (!thread1.isInterrupted())
        new Thread(() -> {
          System.out.println("Start installation: ");
          for (int i = 0; i < 10; i++) {
            try {
              Thread.sleep(200);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
            System.out.print(" .");
          }
          System.out.println("\nCompleted Installation");
        }).start();

    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("\nApp exits.");
  }
}
