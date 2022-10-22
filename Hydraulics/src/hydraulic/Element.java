package hydraulic;

import java.util.Arrays;

/**
 * Represents the generic abstract element of an hydraulics system.
 * It is the base class for all elements.
 *
 * Any element can be connect to a downstream element
 * using the method {@link #connect(Element) connect()}.
 */
public abstract class Element {
	
	/**
	 * Constructor
	 * @param name the name of the element
	 */
	private String name;
	protected int numOutput = 0;
	protected Element[] outputs;
	protected Element input;
	
	public Element(String name){
		this.name = name;
		this.numOutput = 1;
		this.input = null;
		outputs = new Element[1];
	}
	
	public Element(String name, int numOutputs){
		this.name = name;
		this.numOutput = numOutputs;
		this.input = null;
		outputs = new Element[numOutputs];
	}

	/**
	 * getter method
	 * @return the name of the element
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Connects this element to a given element.
	 * The given element will be connected downstream of this element
	 * @param elem the element that will be placed downstream
	 */
	
	public void connect(Element elem){
		if(this instanceof Sink) {
			System.err.println("Errore: un elemento Sink non ha un'uscita!");
			this.outputs[0] = null;
			return;
		}
		elem.setInput(this);
		this.outputs[0] = elem;
	}
	
	/**
	 * Retrieves the element connected downstream of this
	 * @return downstream element
	 */
	public Element getOutput(){
		return this.outputs[0];
	}

	/**
	 * Retrieves the element connected upstream of this
	 * @return upstream element
	 */
	public Element getInput(){
		return this.input;
	}
	
	public void setInput(Element e) {
		this.input = e;
	}
	
	public double getInFlow(Element prev) {
		if(this instanceof Source || (this.getInput() instanceof Tap && ((Tap)this.getInput()).open == false) ) {
			return 0;
		}
		if(prev.getInput() instanceof Source) {
			return ((Source)prev.getInput()).flow;
		}
		if( ((prev.getInput() instanceof Tap && ((Tap)prev.getInput()).open == true))
				|| (prev.getInput() instanceof Sink) ) {
			return prev.getInFlow(prev.getInput());
		}
		if(prev.getInput() instanceof Split || prev.getInput() instanceof Multisplit) {
			return prev.getInFlow(prev.getInput()) / ((double) ((Split)prev).numOutput);
		}
		return 0;
		
	}
	
	protected void simulate(double inFlow, SimulationObserver observer) {
		simulate(inFlow,new SimulationObserverExt() {
			@Override
			public void notifyFlow(String type, String name, double inFlow, double... outFlow) {
				observer.notifyFlow(type, name, inFlow, outFlow);
			}
			@Override
			public void notifyFlowError(String type, String name, double inFlow, double maxFlow) {
				// ignore notification
			}
		},false);
	}

	protected abstract void simulate(double inFlow, SimulationObserverExt observer, boolean enableMaxFlowChecks);
	
	protected abstract StringBuffer layout(String padding);
	
	/**
	 * Returns a string consisting of 'n' blanks
	 * 
	 * @param n number of blanks to generate
	 * @return
	 */
	protected static String blanks(int n) {
		char[] res = new char[n];
		Arrays.fill(res, ' ');
		return new String(res);
	}
}
