package com.lyl.web.base.Entity;

import com.lyl.web.base.BaseEntity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

public abstract class AutoIncrementKeyBaseDomain<ID> extends BaseEntity<ID> {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    protected ID id;

    public AutoIncrementKeyBaseDomain() {
    }

    public ID getId() {
        return this.id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            AutoIncrementKeyBaseDomain<?> that = (AutoIncrementKeyBaseDomain)o;
            return Objects.equals(this.id, that.id);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.id});
    }
}

