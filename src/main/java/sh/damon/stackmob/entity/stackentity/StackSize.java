package sh.damon.stackmob.entity.stackentity;

import dev.onyxstudios.cca.api.v3.component.Component;
import sh.damon.stackmob.StackMobComponent;

public interface StackSize extends Component {
    static <T> StackSize get(T provider) {
        return StackMobComponent.STACK_SIZE.get(provider);
    }

    int getSize();
    void setSize(int newSize);
}
