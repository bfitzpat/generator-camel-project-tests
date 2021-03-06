/*
 * Copyright (C) 2018 Red Hat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.djelinek.generator_camel_project_tests;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Contains some utils methods used in various step definitions
 */
public class Utils {

	public static String getProcessOutPut(Process process, boolean printOut) throws IOException {
		StringBuilder builder = new StringBuilder();
		try (InputStreamReader is = new InputStreamReader(process.getInputStream());
				BufferedReader reader = new BufferedReader(is)) {
			String line = reader.readLine();
			while (line != null) {
				builder.append(line);
				if (printOut) {
					System.out.println(line);
				}
				line = reader.readLine();
			}
		}
		return builder.toString();
	}

	public static boolean checkAndlogProcessOutput(Process process, String checkText) throws IOException {
		boolean isConditionMet = false;
		try (InputStreamReader is = new InputStreamReader(process.getInputStream());
				BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
			String line = reader.readLine();
			while (line != null && !isConditionMet) {
				System.out.println(line);
				if (line.contains(checkText)) {
					isConditionMet = true;
					break;
				}
				line = reader.readLine();
			}
		}
		return isConditionMet;
	}
	
	public static String setProcessConsoleInput(Process process, boolean printOut, String... input) throws IOException {
		StringBuilder builder = new StringBuilder();
		try (InputStreamReader is = new InputStreamReader(process.getInputStream());
				BufferedReader reader = new BufferedReader(is)) {
			String line = reader.readLine();
			int lineNumber = 0;
			int i = 0;
			while (line != null) {
				lineNumber++;
				BufferedWriter out = null;
				if((out = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()))) != null && lineNumber > 15) {
					out.write(input[i]);
					out.flush();
					if(i < input.length-1) {
						i++;
					}
				}
				builder.append(line);
				if (printOut) {
					System.out.println(line);
				}
				line = reader.readLine();
			}
		}
		return builder.toString();
	}
}
