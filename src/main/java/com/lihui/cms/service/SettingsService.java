package com.lihui.cms.service;

import com.lihui.cms.domain.Settings;

public interface SettingsService {

	void registerAdmin(Settings settings);

	Settings loginAdmin(Settings settings);

}
