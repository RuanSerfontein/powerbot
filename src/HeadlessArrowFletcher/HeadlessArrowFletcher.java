package scripts;

import org.powerbot.script.*;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.Random;
import java.util.concurrent.Callable;

@Script.Manifest(name="Headless Arrow Fletcher", description = "Fletches Headless arrows from Arrow shafts and Feathers", properties = "author=RSBotGmail, topic = 1336414, client=6")

public class HeadlessArrowFletcher extends PollingScript<ClientContext> {

    private void clickRandomPlayer(){
        ctx.players.select().shuffle().poll().click(false);
        Condition.sleep(Random.nextInt(1000, 2000));
    }

    private void randomCameraMovements(){
        int currentYaw = ctx.camera.yaw();

        ctx.camera.angle(currentYaw + Random.nextInt(-180, 180));
        ctx.camera.pitch(Random.nextInt(40, 100));
    }

    @Override
    public void start(){
        System.out.println("Starting script");
    }

    @Override
    public void stop(){
        System.out.println("Stopping script");
    }


    @Override
    public void poll() {
        if (ctx.menu.opened()){
            ctx.menu.close();
        }

        if (ctx.widgets.widget(1251).component(5).visible()) {
            if (Random.nextInt(0, 10)==0){
                randomCameraMovements();
                Condition.sleep(Random.nextInt(500, 1000));
                clickRandomPlayer();
                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return !(ctx.widgets.widget(1251).component(5).visible());
                    }
                });
                return;
            }
            if (Random.nextInt(0, 5)==0){
                clickRandomPlayer();
                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return !(ctx.widgets.widget(1251).component(5).visible());
                    }
                });
                return;
            }
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return !(ctx.widgets.widget(1251).component(5).visible());
                }
            });
            return;
        }

        if (ctx.widgets.widget(1370).component(38).visible()) {
            Condition.sleep(Random.nextInt(100, 200));
            if (Random.nextInt(0, 5) == 0) {
                ctx.widgets.widget(1370).component(38).click();
            } else {
                ctx.input.send("{VK_SPACE down}");
                Condition.sleep(Random.nextInt(20, 100));
                ctx.input.send("{VK_SPACE up}");
            }
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return !(ctx.widgets.widget(1370).component(38).visible());
                }
            });
            return;
        }

        if ((ctx.backpack.select().id(314).count() > 0) && (ctx.backpack.select().id(52).count() > 0) && !(ctx.widgets.widget(1251).component(5).visible()) && !(ctx.widgets.widget(1370).component(38).visible()) && (ctx.client().getClientState()==8)) {
            System.out.println(ctx.client().getClientState());

            ctx.backpack.select().id(52).poll().click();
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return ctx.widgets.widget(1370).component(38).visible();
                }
            }, 1000, 3);
        }
    }
}