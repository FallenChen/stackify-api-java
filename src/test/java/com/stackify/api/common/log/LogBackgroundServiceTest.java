/*
 * Copyright 2014 Stackify
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stackify.api.common.log;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.stackify.api.common.http.HttpException;

/**
 * LogBackgroundService JUnit Test
 * 
 * @author Eric Martin
 */
public class LogBackgroundServiceTest {
	
	/**
	 * testConstructor
	 */
	@Test
	public void testConstructor() {
		LogCollector collector = Mockito.mock(LogCollector.class);
		LogSender sender = Mockito.mock(LogSender.class);
		LogBackgroundService service = new LogBackgroundService(collector, sender);

		Assert.assertNotNull(service.scheduler());
	}
		
	/**
	 * testShutDown
	 * @throws Exception 
	 */
	@Test
	public void testShutDown() throws Exception {
		LogCollector collector = Mockito.mock(LogCollector.class);
		LogSender sender = Mockito.mock(LogSender.class);
		LogBackgroundService service = new LogBackgroundService(collector, sender);
		
		service.shutDown();
		
		Mockito.verify(collector).flush(Mockito.any(LogSender.class));
	}
	
	/**
	 * testRunOneIteration
	 * @throws HttpException 
	 * @throws IOException 
	 */
	@Test
	public void testRunOneIteration() throws IOException, HttpException {
		LogCollector collector = Mockito.mock(LogCollector.class);
		LogSender sender = Mockito.mock(LogSender.class);
		LogBackgroundService service = new LogBackgroundService(collector, sender);
				
		service.runOneIteration();
		
		Mockito.verify(collector).flush(Mockito.any(LogSender.class));
	}
}
