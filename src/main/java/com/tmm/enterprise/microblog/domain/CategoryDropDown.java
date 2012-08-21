package com.tmm.enterprise.microblog.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Category")
public class CategoryDropDown extends DropDownData {

}
