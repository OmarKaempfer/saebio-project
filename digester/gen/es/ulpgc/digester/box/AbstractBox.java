package es.ulpgc.digester.box;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import io.intino.alexandria.logger.Logger;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;

public abstract class AbstractBox extends io.intino.alexandria.core.Box {
	protected DigesterConfiguration configuration;
	private io.intino.alexandria.terminal.Connector connector;
	private es.ulpgc.datahub.DigesterTerminal terminal;
	private io.intino.alexandria.datalake.Datalake datalake;

	public AbstractBox(String[] args) {
		this(new DigesterConfiguration(args));
	}

	public AbstractBox(DigesterConfiguration configuration) {
		this.configuration = configuration;
		initJavaLogger();
		this.connector = new io.intino.alexandria.terminal.JmsConnector(configuration().get("datahub_url"), configuration().get("datahub_user"), configuration().get("datahub_password"), configuration().get("datahub_clientId"), new java.io.File(configuration().get("datahub_outbox_directory")));
		this.datalake = new io.intino.alexandria.datalake.file.FileDatalake(new java.io.File(configuration().get("datalake_directory")));
		this.terminal = new es.ulpgc.datahub.DigesterTerminal(connector);
	}

	public DigesterConfiguration configuration() {
		return configuration;
	}

	@Override
	public io.intino.alexandria.core.Box put(Object o) {
		return this;
	}

	public abstract void beforeStart();

	public io.intino.alexandria.core.Box start() {
		if (owner != null) owner.beforeStart();
		beforeStart();
		if (owner != null) owner.start();
		initUI();
		initConnector();
		initRestServices();
		initRestServices();
		initSoapServices();
		initJmxServices();
		initDatalake();
		initTerminal();
		initMessagingServices();
		initSentinels();
		initSlackBots();
		initWorkflow();
		if (owner != null) owner.afterStart();
		afterStart();
		return this;
	}

	public abstract void afterStart();

	public abstract void beforeStop();

	public void stop() {
		if (owner != null) owner.beforeStop();
		beforeStop();
		if (owner != null) owner.stop();
		if (connector instanceof io.intino.alexandria.terminal.JmsConnector) ((io.intino.alexandria.terminal.JmsConnector) connector).stop();
		if (owner != null) owner.afterStop();
		afterStop();
	}

	public abstract void afterStop();

	public es.ulpgc.datahub.DigesterTerminal terminal() {
		return this.terminal;
	}

	protected io.intino.alexandria.terminal.Connector datahubConnector() {
		return this.connector;
	}

	public io.intino.alexandria.datalake.Datalake datalake() {
		return this.datalake;
	}



	private void initRestServices() {

	}

	private void initSoapServices() {

	}

	private void initMessagingServices() {

	}

	private void initJmxServices() {

	}

	private void initSlackBots() {

	}

	private void initUI() {

	}

	private void initDatalake() {
	}

	private void initConnector() {
		if (connector instanceof io.intino.alexandria.terminal.JmsConnector) ((io.intino.alexandria.terminal.JmsConnector) connector).start();
	}

	private void initTerminal() {
		if (this.terminal != null) {
			this.terminal.subscribe((es.ulpgc.datahub.DigesterTerminal.SampleConsumer) e -> new es.ulpgc.digester.box.subscribers.SampleSubscriber((DigesterBox) AbstractBox.this).accept(e), "sampleDigestion");
			this.terminal.subscribe((es.ulpgc.datahub.DigesterTerminal.AntibiogramConsumer) e -> new es.ulpgc.digester.box.subscribers.AntibiogramSubscriber((DigesterBox) AbstractBox.this).accept(e), "antibiogramDigestion");
			this.terminal.subscribe((es.ulpgc.datahub.DigesterTerminal.CultureConsumer) e -> new es.ulpgc.digester.box.subscribers.CultureSubscriber((DigesterBox) AbstractBox.this).accept(e), "cultureDigestion");
		}
	}

	private void initSentinels() {
	}

	private void initWorkflow() {
	}

	private void initJavaLogger() {
		final java.util.logging.Logger Logger = java.util.logging.Logger.getGlobal();
		final ConsoleHandler handler = new ConsoleHandler();
		handler.setLevel(Level.INFO);
		handler.setFormatter(new io.intino.alexandria.logger.Formatter());
		Logger.setUseParentHandlers(false);
		Logger.addHandler(handler);
	}

	protected java.net.URL url(String url) {
		try {
			return new java.net.URL(url);
		} catch (java.net.MalformedURLException e) {
			return null;
		}
	}
}