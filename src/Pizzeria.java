import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class Pizzeria {

    Long startTime;
    LinkedBlockingDeque<Order> orders = new LinkedBlockingDeque<>();

    public Pizzeria() {
        startTime = System.currentTimeMillis();
        new PizzaCar().start();
        new PizzaCar().start();
    }

    class PizzaCar extends Thread {
        @Override
        public void run() {
        while (System.currentTimeMillis() - startTime < 5000){
            Order order = null;
            try {
                order = orders.poll(10, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                break;
            }
            if (order != null) {
                if (System.currentTimeMillis() - order.time + 500 <= 750) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(order.name + " доставлена");
                } else {
                    System.out.println(order.name + " не готова и не доставлена");
                }
            }
        }
        }
    }


    void order(String pizzaName) {
        try {
            orders.put(new Order(pizzaName, System.currentTimeMillis()));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    ;

    class Order {
        String name;
        long time;

        public Order(String name, Long time) {
            this.name = name;
            this.time = time;
        }
    }
    public static void main(String[] args) throws InterruptedException {

        Pizzeria pizzeria = new Pizzeria();
        pizzeria.order("Margarita");
        Thread.sleep(100);
        pizzeria.order("Pepperoni");
        Thread.sleep(100);
        pizzeria.order("Sicilian");
        Thread.sleep(100);
        pizzeria.order("Greek");

    }
}