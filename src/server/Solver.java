package server;

import Classs.*;
import Interface.*;

public interface Solver<T> {
	// the solve method
	public Solution<T> solve(Searchable<T> s);
	
}
