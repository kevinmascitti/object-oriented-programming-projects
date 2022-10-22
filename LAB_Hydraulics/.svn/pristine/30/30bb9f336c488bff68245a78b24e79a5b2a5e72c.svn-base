package hydraulic;

/**
 * Represents the sink, i.e. the terminal element of a system
 *
 */
public class Sink extends ElementExt {

	/**
	 * Constructor
	 * @param name
	 */
	
	public Sink(String name) {
		super(name);
	}
	
	@Override
	protected StringBuffer layout(String pad) {
		StringBuffer res = new StringBuffer();
		res.append("[").append(getName()).append("]Sink *");
		return res;
	}
	
	@Override
	protected void simulate(double inFlow, SimulationObserverExt observer, boolean enableMaxFlowChecks) {
		if(enableMaxFlowChecks && inFlow>maxFlow)
			observer.notifyFlowError(this.getClass().getName(), getName(), inFlow, maxFlow);
		observer.notifyFlow("Sink", getName(), inFlow, SimulationObserver.NO_FLOW);
	}
	
}
