package es.ulpgc.datahub;

import io.intino.alexandria.core.Box;
import io.intino.datahub.box.DataHubBox;
import io.intino.datahub.graph.NessGraph;
import io.intino.magritte.framework.Graph;

public class Main {

	private static final String[] Stashes = {"Solution"};

	public static void main(String[] args) {
		NessGraph nessGraph = new Graph().loadStashes(Stashes).as(NessGraph.class);
		Box box = new DataHubBox(args).put(nessGraph.core$()).start();
		Runtime.getRuntime().addShutdownHook(new Thread(box::stop));
	}
}