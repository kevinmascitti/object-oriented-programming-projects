package hydraulic;

/**
 * Represents a tap that can interrupt the flow.
 * 
 * The status of the tap is defined by the method
 * {@link #setOpen(boolean) setOpen()}.
 */

public class Tap extends ElementExt {

	private int indice;
	
	public Tap(String name) {
		super(name);
	}
	
	protected boolean open;
	public void setOpen(boolean open){
		this.open = open;
	}
	
	@Override
	protected StringBuffer layout(String pad) {
		StringBuffer res = new StringBuffer();
		res.append("[").append(getName()).append("]Tap -> ");
		res.append( getOutput().layout( pad + blanks(res.length()) ) );
		return res;
	}
	
	@Override
	protected void simulate(double inFlow, SimulationObserverExt observer, boolean enableMaxFlowChecks) {
		double outFlow = (open?inFlow:0);
		observer.notifyFlow("Tap",getName(),inFlow, outFlow);
		if(enableMaxFlowChecks && inFlow>maxFlow)
			observer.notifyFlowError(this.getClass().getName(), getName(), inFlow, maxFlow);
		getOutput().simulate(outFlow,observer,enableMaxFlowChecks);
	}

}
