package com.base.bgwork;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.base.util.Log;
import com.platform.util.FileUtil;

/**
 * @author Muhil 
 * runs spring scheduled task every 6 hours to clean temp files over 24hours.
 */
@Component
public class TempFileCleanUpScheduledTask extends BGTask {

	@Scheduled(fixedDelay = 21600000)
	@Override
	public void schedule() {
		this.run();
	}

	@Override
	public void run() {
		Log.base.info("Starting TempFileCleanUpScheduledTask");
		FileUtil.cleanUpTempDirectory();
	}

}
