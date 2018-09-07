package Classs;

import java.io.Serializable;

public class State<T> implements Comparable<State<T>>,Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private T state; // the state represented by a T
	private double cost; // cost to reach this state
	private State<T> cameFrom; // the state we came from to this state
	
	public State() {
		this.state=null;
		this.cost=Double.POSITIVE_INFINITY;
		this.cameFrom=null;
	}
	public State(T state){ // CTOR
		this.state= state;
		this.cost=0;
		this.cameFrom=null;
	}
	public State(T state,double cost,State<T> cameFrom){ // CTOR
		this.state= state;
		this.cost=cost;
		this.cameFrom=cameFrom;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof State))
			return false;	
		if (obj == this)
			return true;
		return this.equals(((State<T>) obj));
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return this.state.hashCode();//+(int)this.cost;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.state.toString();
	}
	
	public boolean equals(State<T> s){ // it’s easier to simply overload
		return this.state.equals(s.state);//&&this.cost==s.cost;
	}
	
	public T getState() {
		return state;
	}
	public double getCost() {
		return cost;
	}
	public double getCost(State<T> s) {
		return s.getCost();
	}
	public State<T> getCameFrom() {
		return cameFrom;
	}
	public void setCameFrom(State<T> cameFrom) {
		this.cameFrom = cameFrom;
	}
	public void setState(T state) {
		this.state = state;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	@Override
	public int compareTo(State<T> arg0) {
		double d=this.getCost()-arg0.getCost();
		if(d==0)
		{
			return 0;
		}
		else if(0<d)//arg0.getCost()<this.getCost()
		{
			return (int) Math.ceil(d);
		}
		else//if(d<0)//this.getCost()<arg0.getCost()
		{
			return (int)Math.floor(d);
		}
	}
	
	
	//protected abstract Collection<State<T>> getNeighbors();
	
}
