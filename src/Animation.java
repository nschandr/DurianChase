public class Animation implements Action {
    private AnimatedEntity entity;
    private int repeatCount;

    public Animation(AnimatedEntity entity, int repeatCount)
    {
        this.entity = entity;
        this.repeatCount = repeatCount;
    }
    public int getRepeatCount(){
        return repeatCount;
    }

    public void executeAction(EventScheduler scheduler){
        entity.executeAnimationAction(scheduler, this);
    }
}
