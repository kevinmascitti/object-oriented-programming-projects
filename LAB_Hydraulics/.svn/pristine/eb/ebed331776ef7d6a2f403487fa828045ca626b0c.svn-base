package hydraulic;

/**
 * Represents a split element, a.k.a. T element
 * 
 * During the simulation each downstream element will
 * receive a stream that is half the input stream of the split.
 */

public class Split extends ElementExt {

	/**
	 * Constructor
	 * @param name
	 */	
	public Split(String name) {
		super(name, 2);
	}
	
	protected Split(String name, int numOutput) {
		super(name, numOutput);
	}
    
	/**
	 * returns the downstream elements
	 * @return array containing the two downstream element
	 */
    
    public Element[] getOutputs(){
        return outputs;
    }

    /**
     * connect one of the outputs of this split to a
     * downstream component.
     * 
     * @param elem  the element to be connected downstream
     * @param noutput the output number to be used to connect the element
     */
    
	public void connect(Element elem, int noutput){
		if(noutput>=0 && noutput<2) {
			if(this.outputs[noutput] == null)
				this.outputs[noutput] = elem;
			else {
				System.err.println("Errore: lo split ha già un elemento connesso all'uscita "+noutput);
			}
		}
		else {
			System.err.println("Errore: lo split ha solo 2 uscite!");
			return;
		}
	}
	
	@Override
	protected StringBuffer layout(String pad) {
		StringBuffer res = new StringBuffer();
		res.append("[").append(getName()).append("]Split");
		String padding = pad + blanks(res.length());
		res.append(" +-> ").append( getOutputs()[0].layout( padding + "     " ) );
		res.append("\n").append( padding ).append(" |\n");
		res.append( padding );
		res.append(" +-> ").append( getOutputs()[1].layout( padding + "     " ) );
		return res;
	}
	
	@Override
	protected void simulate(double inFlow, SimulationObserverExt observer, boolean enableMaxFlowChecks) {
		double outFlow = inFlow/2;

		observer.notifyFlow("Split", getName(), inFlow, outFlow, outFlow);
		if(enableMaxFlowChecks && inFlow>maxFlow)
			observer.notifyFlowError(this.getClass().getName(), getName(), inFlow, maxFlow);
		for(Element e : getOutputs()){
			e.simulate(outFlow,observer,enableMaxFlowChecks);
		}
	}
}
