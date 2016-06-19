package com.stobinski.bottlecaps.ejb.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import org.junit.Before;
import org.junit.Test;

import com.stobinski.bottlecaps.ejb.dao.exceptions.ColumnsValuesNotMatchException;
import com.stobinski.bottlecaps.ejb.dao.exceptions.FromClassLackException;
import com.stobinski.bottlecaps.ejb.dao.exceptions.MultipleInvocationException;
import com.stobinski.bottlecaps.ejb.dao.exceptions.SqlFunctionLackException;
import com.stobinski.bottlecaps.ejb.entities.Caps;

public class QueryBuilderTest {

	private QueryBuilder queryBuilder;
	
	@Before
	public void init() {
		this.queryBuilder = new QueryBuilder();
	}
	
	@Test
	public void shouldBuildFirstPartOfQuery() {
		// given
		QueryBuilder queryBuilderSpy = spy(queryBuilder);
		
		// when
		doReturn(Caps.class).when(queryBuilderSpy).getEntity();
		
		// then
		assertThat(queryBuilderSpy.select().build().toString()).contains("SELECT");
	}

	@Test
	public void shouldThrowExceptionWhenBuiltWithoutSqlFunction() {
		// expected
		assertThatThrownBy(() -> { queryBuilder.build(); } ).isInstanceOf(SqlFunctionLackException.class);
	}
	
	@Test
	public void shouldThrowExceptionWhenSqlFunctionIsCalledOneThanMoreTime() {
		// expected
		assertThatThrownBy(() -> { queryBuilder.select().select().build(); } ).isInstanceOf(MultipleInvocationException.class);
	}
	
	@Test
	public void shouldBuildTwoPartQuery() {
		// given
		String expectedQuery = "SELECT e FROM " + Caps.class.getSimpleName() + " e";
		// when
		StringQuery query = queryBuilder.select().from(Caps.class).build();
		// then
		assertThat(query.toString()).isEqualTo(expectedQuery);
	}
	
	@Test
	public void shouldThrowExceptionWhenBuiltWithoutGivingClass() {
		// expected
		assertThatThrownBy(() -> { queryBuilder.select().build(); } ).isInstanceOf(FromClassLackException.class);		
	}
	
	@Test
	public void shouldThrowExceptionWhenFromIsCalledOneThanMoreTime() {
		// expected
		assertThatThrownBy(() -> { queryBuilder.select().from(Caps.class).from(Caps.class).build(); } ).isInstanceOf(MultipleInvocationException.class);
	}
	
	@Test
	public void shouldContainsWhereWhenInvokedWhereMethod() {
		// given
		QueryBuilder queryBuilderSpy = spy(queryBuilder);
		// when
		doReturn(new Object[] {"spy", "spy"}).when(queryBuilderSpy).getValues();
		doReturn(new String[] {"spy", "spy"}).when(queryBuilderSpy).getColumns();
		// then
		assertThat(queryBuilderSpy.select().from(Caps.class).where().build().toString()).contains("WHERE");
	}
	
	@Test
	public void shouldContainsColumnsNames() {
		// given
		QueryBuilder queryBuilderSpy = spy(queryBuilder);
		// when
		doReturn(new Object[] {"spy", "spy"}).when(queryBuilderSpy).getValues();
		// then
		assertThat(queryBuilderSpy.select().from(Caps.class).where(Caps.BEER_NAME, Caps.CAP_TEXT_NAME).build().toString()).contains(Caps.BEER_NAME, Caps.CAP_TEXT_NAME);
	}
	
	@Test
	public void shouldFilteredQueryHaveColumnsAndValues() {
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
	public void shouldAppendLikeToQuery() {
		// given
		String expectedQuery = "SELECT e FROM " + Caps.class.getSimpleName() + " e WHERE e." + Caps.BEER_NAME + " LIKE :likeValue1";
		// when
		StringQuery query = queryBuilder.select().from(Caps.class).where(Caps.BEER_NAME).like("Karlovacko").build();
		// then
		assertThat(query.toString()).isEqualTo(expectedQuery);
	}

	@Test
	public void shouldThrowExceptionWhenNumberOfColumnsAndLikeValuesDontMatch() {
		// expected
		assertThatThrownBy(() -> { queryBuilder.select().from(Caps.class).where(Caps.BEER_NAME).like("OK", "OK").build(); } ).isInstanceOf(ColumnsValuesNotMatchException.class);
	}
	
	@Test
	public void shouldBuildProperCountQuery() {
		// given
		String expectedQuery = "SELECT COUNT(e) FROM " + Caps.class.getSimpleName() + " e";
		// when
		StringQuery query = queryBuilder.count().from(Caps.class).build();
		// then
		assertThat(query.toString()).isEqualTo(expectedQuery);
	}
	
}
