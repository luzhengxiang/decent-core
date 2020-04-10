package com.dingsheng.decent.dto.admin.System;

import java.util.List;

public class ModuleModel {
    public Integer id;
    public String text;
    public List<ModuleParentModel> children;
}