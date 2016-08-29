package com.stobinski.bottlecaps.ejb.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.stobinski.bottlecaps.ejb.dao.exceptions.ColumnsValuesNotMatchException;
import com.stobinski.bottlecaps.ejb.dao.exceptions.InMultipleColumnsException;
import com.stobinski.bottlecaps.ejb.dao.exceptions.MultipleInvocationException;
import com.stobinski.bottlecaps.ejb.dao.exceptions.OrderByException;
import com.stobinski.bottlecaps.ejb.dao.exceptions.QueryBuilderException;
import com.stobinski.bottlecaps.ejb.dao.exceptions.SqlFunctionLackException;
import com.stobinski.bottlecaps.ejb.entities.Caps;
import com.stobinski.bottlecaps.ejb.entities.News;

public class QueryBuilderTest {

	private QueryBuilder queryBuilder;
	
	@Before
	public void init() {
		this.queryBuilder = new QueryBuilder();
	}
	
	@Test
	public void shouldBuildFirstPartOfSelectQuery() throws QueryBuilderException {
		// when
		String query = queryBuilder.select().from(Caps.class).build().toString();
		
		// then
		assertThat(query).contains("SELECT");
	}

	@Test
	public void shouldThrowExceptionWhenBuiltWithoutSqlFunction() {
		// expected
		assertThatThrownBy(() -> { queryBuilder.build(); } ).isInstanceOf(SqlFunctionLackException.class);
	}
	
	@Test
	public void shouldThrowExceptionWhenSqlFunctionIsCalledOneThanMoreTime() {
		// expected
		assertThatThrownBy(() -> { queryBuilder.select().from(Caps.class).select().from(Caps.class).build(); } ).isInstanceOf(MultipleInvocationException.class);
	}
	
	@Test
	public void shouldBuildTwoPartQuery() throws QueryBuilderException {
		// given
		String expectedQuery = "SELECT e FROM " + Caps.class.getSimpleName() + " e";
		// when
		StringQuery query = queryBuilder.select().from(Caps.class).build();
		// then
		assertThat(query.toString()).isEqualTo(expectedQuery);
	}
	
	@Test
	public void shouldThrowExceptionWhenFromIsCalledOneThanMoreTime() {
		// expected
		assertThatThrownBy(() -> { queryBuilder.select().from(Caps.class).select().from(Caps.class).build(); } ).isInstanceOf(MultipleInvocationException.class);
	}
	
	@Test
	public void shouldFilteredQueryHaveColumnsAndValues() throws QueryBuilderException {
		// given
		String expectedQuery = "SELECT e FROM " + Caps.class.getSimpleName() + " e WHERE e." + Caps.BEER_NAME + "=?1 AND e." + Caps.CAP_TEXT_NAME + "=?2";
		// when
		StringQuery query = queryBuilder.select().from(Caps.class).where(Caps.BEER_NAME, Caps.CAP_TEXT_NAME).eq(1, "Karlovacko").build();
		// then
		assertThat(query.toString()).isEqualTo(expectedQuery);
	}

	@Test
	public void shouldThrowExceptionWhenNumberOfColumnsAndValuesDontMatch() {
		// expected
		assertThatThrownBy(() -> { queryBuilder.select().from(Caps.class).where(Caps.BEER_NAME).eq(1, "OK").build(); } ).isInstanceOf(ColumnsValuesNotMatchException.class);
	}
	
	@Test
	public void shouldAppendLikeToQuery() throws QueryBuilderException {
		// given
		String expectedQuery = "SELECT e FROM " + Caps.class.getSimpleName() + " e WHERE e." + Caps.BEER_NAME + " LIKE :likeValue1";
		// when
		StringQuery query = queryBuilder.select().from(Caps.class).where(Caps.BEER_NAME).like("Karlovacko").build();
		// then
		assertThat(query.toString()).isEqualTo(expectedQuery);
	}

	@Test
	public void shouldAppendOrderByToQuery() throws QueryBuilderException {
		// given
		String expectedQuery = "SELECT e FROM " + News.class.getSimpleName() + " e ORDER BY e." + News.DATE_NAME;
		
		// when
		StringQuery query = queryBuilder.select().from(News.class).orderBy(News.DATE_NAME).build();
		
		// then
		assertThat(query.toString()).isEqualTo(expectedQuery);
	}

	@Test
	public void shouldAppendAscToOrderByQuery() throws QueryBuilderException {
		// given
		String expectedQuery = "SELECT e FROM " + News.class.getSimpleName() + " e ORDER BY e." + News.DATE_NAME + " ASC";
	
		// when
		StringQuery query = queryBuilder.select().from(News.class).orderBy(News.DATE_NAME).asc().build();
	
		// then
		assertThat(query.toString()).isEqualTo(expectedQuery);
	}

	@Test
	public void shouldAppendDescToOrderByQuery() throws QueryBuilderException {
		// given
		String expectedQuery = "SELECT e FROM " + News.class.getSimpleName() + " e ORDER BY e." + News.DATE_NAME + " DESC";
		
		// when
		StringQuery query = queryBuilder.select().from(News.class).orderBy(News.DATE_NAME).desc().build();
		
		// then
		assertThat(query.toString()).isEqualTo(expectedQuery);
	}
	
	@Test
	public void shouldThrowExceptionWhenUsedAscAndDescTogether() {
		// expected
		assertThatThrownBy(() -> { queryBuilder.select().from(News.class).orderBy(News.DATE_NAME).asc().desc().build(); } ).isInstanceOf(OrderByException.class);
	}
	
	@Test
	public void shouldThrowExceptionWhenNumberOfColumnsAndLikeValuesDontMatch() {
		// expected
		assertThatThrownBy(() -> { queryBuilder.select().from(Caps.class).where(Caps.BEER_NAME).like("OK", "OK").build(); } ).isInstanceOf(ColumnsValuesNotMatchException.class);
	}
	
	@Test
	public void shouldBuildProperCountQuery() throws QueryBuilderException {
		// given
		String expectedQuery = "SELECT COUNT(e) FROM " + Caps.class.getSimpleName() + " e";
		// when
		StringQuery query = queryBuilder.count().from(Caps.class).build();
		// then
		assertThat(query.toString()).isEqualTo(expectedQuery);
	}
	
	@Test
	public void shouldBuildFirstPartOfUpdateQuery() throws QueryBuilderException {
		// when
		StringQuery query = queryBuilder.update(Caps.class).set(Caps.BEER_NAME).eq("TEST").build();
		// then
		assertThat(query.toString()).contains("UPDATE");
	}
	
	@Test
	public void shouldBuildUpdateQueryWithSet() throws QueryBuilderException {
		// given
		String expectedQuery = "UPDATE " + Caps.class.getSimpleName() + " e SET e." + Caps.BEER_NAME + "=?1, e." + Caps.FILE_NAME_NAME + "=?2";
		// when
		StringQuery query = queryBuilder.update(Caps.class).set(Caps.BEER_NAME, Caps.FILE_NAME_NAME).eq("KORCA", "2").build();
		// then
		assertThat(query.toString()).isEqualTo(expectedQuery);
	}
	
	@Test
	public void shouldBuildUpdateQueryWithSetAndWhere() throws QueryBuilderException {
		// given
		String expectedQuery = "UPDATE " + Caps.class.getSimpleName() + " e SET e." + Caps.BEER_NAME + "=?1, e." + Caps.FILE_NAME_NAME + "=?2 WHERE e." + Caps.ID_NAME + "=?3";
		// when
		StringQuery query = queryBuilder.update(Caps.class).set(Caps.BEER_NAME, Caps.FILE_NAME_NAME).eq("KORCA", "2").where(Caps.ID_NAME).eq(1).build();
		// then
		assertThat(query.toString()).isEqualTo(expectedQuery);
	}
	
	@Test
	public void shouldBuildQueryWithInExpression() throws QueryBuilderException {
		// given
		String expectedQuery = "SELECT e FROM " + Caps.class.getSimpleName() + " e WHERE e." + Caps.ID_NAME + " IN :params";
		Set<Long> set = new HashSet<>();
		set.add(1l);
		// when
		@SuppressWarnings("unchecked")
		StringQuery query = queryBuilder.select().from(Caps.class).where(Caps.ID_NAME).in((Set<Object>)(Set<?>) set).build();
		// then
		assertThat(query.toString()).isEqualTo(expectedQuery);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void shouldThrowExceptionWhenInIsCalledWithMoreThanOneColumns() {
		// given
		Set<Long> set = new HashSet<>();
		set.add(1l);
		
		// expected
		assertThatThrownBy(() -> { queryBuilder.select().from(Caps.class).where(Caps.ID_NAME, Caps.ADDED_DATE_NAME).in((Set<Object>)(Set<?>) set).build(); } ).isInstanceOf(InMultipleColumnsException.class);
	}
	
	@Test
	public void shouldBuildDeleteQuery() throws QueryBuilderException {
		// given
		String expectedQuery = "DELETE FROM " + Caps.class.getSimpleName() + " e";
		// when
		StringQuery query = queryBuilder.delete().from(Caps.class).build();
		// then
		assertThat(query.toString()).isEqualTo(expectedQuery);
	}
	
}
