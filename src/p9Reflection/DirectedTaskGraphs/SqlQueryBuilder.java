package p9Reflection.DirectedTaskGraphs;

import java.util.List;

import p9Reflection.DirectedTaskGraphs.annotations.Annotations.DependsOn;
import p9Reflection.DirectedTaskGraphs.annotations.Annotations.FinalResult;
import p9Reflection.DirectedTaskGraphs.annotations.Annotations.Input;
import p9Reflection.DirectedTaskGraphs.annotations.Annotations.Operation;

public class SqlQueryBuilder {
  @Input("ids")
  private List<String> ids;

  @Input("limit")
  private Integer limit;

  @Input("table")
  private String tableName;

  @Input("columns")
  private List<String> columnNames;

  public SqlQueryBuilder(
      List<String> ids,
      Integer limit,
      String tableName,
      List<String> columnNames) {
    this.ids = ids;
    this.limit = limit;
    this.tableName = tableName;
    this.columnNames = columnNames;
  }

  @Override
  public String toString() {
    return String.format(
        "SqlQueryBuilder [ids=%s, limit=%s, tableName=%s, columnNames=%s]",
        ids,
        limit,
        tableName,
        columnNames);
  }

  @Operation("Select-Builder")
  public String selectStatementBuilder(
      @Input("table") String tableName,
      @Input("columns") List<String> columnNames) {
    String columnsString = columnNames.isEmpty() ? "*" : String.join(", ", columnNames);
    return String.format(
        "SELECT %s FROM %s",
        tableName,
        columnsString);
  }

  @Operation("Where-Clause-Builder")
  public String addWhereClause(
      @DependsOn("Select-Builder") String query,
      @Input("ids") List<String> ids) {
    if (ids.isEmpty()) {
      return query;
    }
    return String.format(
        "%s WHERE id IN (%s)",
        query,
        String.join(", ", ids));
  }

  @FinalResult
  public String addLimit(
      @DependsOn("Where-Clause-Builder") String query,
      @Input("limit") Integer limit) {
    if (limit == null || limit == 0) {
      return query;
    }
    if (limit < 0) {
      throw new IllegalArgumentException("Limit cannot be negative");
    }
    return String.format(
        "%s LIMIT %d",
        query,
        limit.intValue());
  }
}
