package adapter;

import Classs.Solution;
import Interface.Searchable;
import Interface.Searcher;
import server.Solver;

public class SearcherAdapter<T> implements Solver<T> {
	private Searcher<T> searcher;

	public SearcherAdapter(Searcher<T> searcher) {
		this.searcher = searcher;
	}

	@Override
	public Solution<T> solve(Searchable<T> s) {
		return searcher.Search(s);
	}	
}
