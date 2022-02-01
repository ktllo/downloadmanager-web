package org.leolo.web.dm.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.leolo.web.dm.Constant;
import org.leolo.web.dm.dao.SystemParameterDao;


public class QueuedJobs {
	private static QueuedJobs instance;
	private ExecutorService pool = Executors.newFixedThreadPool(new SystemParameterDao().getInt(Constant.SP_QUEUED_JOB_THREAD, 10));
	
	public static synchronized QueuedJobs getInstance() {
		if(instance==null) {
			instance=new QueuedJobs();
		}
		return instance;
	}
	
	private QueuedJobs() {
		
	}
	
	public void queue(Runnable r) {
		pool.execute(r);
	}
}
