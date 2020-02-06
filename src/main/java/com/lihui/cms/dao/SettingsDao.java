package com.lihui.cms.dao;

import com.lihui.cms.domain.Settings;

public interface SettingsDao {

	Settings loginAdmin(Settings settings);

	void registerAdmin(Settings settings);

}
