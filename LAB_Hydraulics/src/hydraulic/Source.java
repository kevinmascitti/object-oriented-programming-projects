package hydraulic;

/**
 * Represents a source of water, i.e. the initial element for the simulation.
 *
 * The status of the source is defined through the method
 * {@link #setFlow(double) setFlow()}.
 */
public class Source extends ElementExt {
	
	public Source(String name) {
		super(name);
	}

	protected double flow;
	public void setFlow(double flow){
		this.flow = flow;
	}
	
	@Override
	protected StringBuffer layout(String pad) {
		StringBuffer res = new StringBuffer(pad);
		res.append("[").append(getName()).append("]Source -> ");
		res.append( getOutput().layout(blanks(res.length())) );
		return res;
	}
	
	@Override
	protected void simulate(double inFlow, SimulationObserverExt observer, boolean enableMaxFlowChecks) {
		observer.notifyFlow("Source",getName(),SimulationObserver.NO_FLOW, flow);
		if(enableMaxFlowChecks && inFlow>maxFlow)
			observer.notifyFlowError(this.getClass().getName(), getName(), inFlow, maxFlow);
		getOutput().simulate(flow,observer,enableMaxFlowChecks);
	}
	
}