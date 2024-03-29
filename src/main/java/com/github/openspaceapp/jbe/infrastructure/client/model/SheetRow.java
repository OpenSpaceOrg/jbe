// Generated by delombok at Mon Aug 07 15:45:02 CEST 2023
package com.github.openspaceapp.jbe.infrastructure.client.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SheetRow {
    List<String> columns;

    public SheetRow(List<Object> objects) {
        this.columns = objects.stream().map(x -> (String) x).collect(Collectors.toList());
    }

    public SheetRow(ArrayList<Object> objects) {
        this.columns = objects.stream().map(x -> (String) x).collect(Collectors.toList());
    }

    public String get(int i) {
        if (columns.size() <= i) {
            return null;
        }
        return columns.get(i);
    }

    @SuppressWarnings("all")
    public List<String> getColumns() {
        return this.columns;
    }

    @Override
    @SuppressWarnings("all")
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof SheetRow)) return false;
        final SheetRow other = (SheetRow) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$columns = this.getColumns();
        final Object other$columns = other.getColumns();
        if (this$columns == null ? other$columns != null : !this$columns.equals(other$columns)) return false;
        return true;
    }

    @SuppressWarnings("all")
    protected boolean canEqual(final Object other) {
        return other instanceof SheetRow;
    }

    @Override
    @SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $columns = this.getColumns();
        result = result * PRIME + ($columns == null ? 43 : $columns.hashCode());
        return result;
    }
}
