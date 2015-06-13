package io.github.podshot.WorldWar;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import org.bukkit.Bukkit;

@SuppressWarnings("unused")
public class UpdatePlugin implements Runnable {

	private WorldWar plugin = WorldWar.getInstance();
	private String update_url;

	public UpdatePlugin(String updateURL) {
		this.update_url = updateURL;
		Bukkit.getScheduler().runTaskAsynchronously(plugin, this);
	}

	@Override
	public void run() {

		//URL updateURL = new URL(this.update_url);
		//ReadableByteChannel rbc = Channels.newChannel(updateURL.openStream());
		//FileOutputStream fos = new FileOutputStream()

		try {
			File mainFolderTestFile = new File("Main-Folder-Test.testFile");
			mainFolderTestFile.createNewFile();

			File pluginFolderTestFile = new File("plugins/Plugin-Folder-Test.testFile");
			pluginFolderTestFile.createNewFile();
		} catch (IOException e) {
			plugin.getLogger().severe("Could not create Test Files!");
			e.printStackTrace();
		}

	}

}
