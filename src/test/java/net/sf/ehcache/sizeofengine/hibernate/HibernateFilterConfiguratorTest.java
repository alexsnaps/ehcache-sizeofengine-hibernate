package net.sf.ehcache.sizeofengine.hibernate;

import net.sf.ehcache.pool.SizeOfEngine;
import net.sf.ehcache.pool.SizeOfEngineLoader;
import net.sf.ehcache.pool.impl.DefaultSizeOfEngine;
import org.hibernate.*;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.engine.jdbc.LobCreator;
import org.hibernate.engine.jdbc.connections.spi.JdbcConnectionAccess;
import org.hibernate.engine.jdbc.spi.JdbcCoordinator;
import org.hibernate.engine.jdbc.spi.JdbcServices;
import org.hibernate.engine.query.spi.sql.NativeSQLQuerySpecification;
import org.hibernate.engine.spi.*;
import org.hibernate.engine.spi.PersistenceContext;
import org.hibernate.jdbc.ReturningWork;
import org.hibernate.jdbc.Work;
import org.hibernate.loader.custom.CustomQuery;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.spi.NativeQueryImplementor;
import org.hibernate.query.spi.QueryImplementor;
import org.hibernate.query.spi.ScrollableResultsImplementor;
import org.hibernate.resource.jdbc.spi.JdbcSessionContext;
import org.hibernate.resource.transaction.spi.TransactionCoordinator;
import org.hibernate.stat.SessionStatistics;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.*;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.Metamodel;
import java.io.Serializable;
import java.sql.Connection;
import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HibernateFilterConfiguratorTest {

  private SizeOfEngine filteringSizeOfEngine;
  private SizeOfEngine defaultSizeOfEngine;

  @Before
  public void setup() {
    filteringSizeOfEngine = SizeOfEngineLoader.newSizeOfEngine(10, true, true);
    defaultSizeOfEngine = new DefaultSizeOfEngine(10, true, true);
  }

  @Test
  public void testLoadsAllFields() {
    assertThat(HibernateFilterConfigurator.ignoredFields.size(), is(8));
  }

  @Test
  public void testFiltersSessionClassInstances() {
    SessionObject sessionObject = new SessionObject();
    final long withoutFiltering = defaultSizeOfEngine.sizeOf(sessionObject, sessionObject, sessionObject).getCalculated();
    final long withFiltering = filteringSizeOfEngine.sizeOf(sessionObject, sessionObject, sessionObject).getCalculated();
    assertThat(withFiltering < withoutFiltering, is(true));
  }

  @Test
  public void testFiltersSessionImplementorClassInstances() {
    SessionImplementorObject sessionImplementorObject = new SessionImplementorObject();
    final long withoutFiltering = defaultSizeOfEngine.sizeOf(sessionImplementorObject, sessionImplementorObject, sessionImplementorObject).getCalculated();
    final long withFiltering = filteringSizeOfEngine.sizeOf(sessionImplementorObject, sessionImplementorObject, sessionImplementorObject).getCalculated();
    assertThat(withFiltering < withoutFiltering, is(true));
  }

  @SuppressWarnings("rawtypes")
  public static class SessionObject {
    Session session = new Session() {
      @Override
      public Session getSession() {
        return null;
      }

      @Override
      public SharedSessionBuilder sessionWithOptions() {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public void flush() throws HibernateException {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public void setFlushMode(FlushModeType flushModeType) {
      }

      @Override
      public void setFlushMode(final FlushMode flushMode) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public FlushModeType getFlushMode() {
        return null;
      }

      @Override
      public void lock(Object o, LockModeType lockModeType) {
      }

      @Override
      public void lock(Object o, LockModeType lockModeType, Map<String, Object> map) {
      }

      @Override
      public void setHibernateFlushMode(FlushMode flushMode) {
      }

      @Override
      public FlushMode getHibernateFlushMode() {
        return null;
      }

      @Override
      public void setCacheMode(final CacheMode cacheMode) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public CacheMode getCacheMode() {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public SessionFactory getSessionFactory() {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public void close() throws HibernateException {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public void cancelQuery() throws HibernateException {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public boolean isOpen() {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public boolean isConnected() {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public boolean isDirty() throws HibernateException {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public boolean isDefaultReadOnly() {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public void setDefaultReadOnly(final boolean b) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public Serializable getIdentifier(final Object o) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public boolean contains(String entityName, Object object) {
        return false;
      }

      @Override
      public boolean contains(final Object o) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public LockModeType getLockMode(Object o) {
        return null;
      }

      @Override
      public void setProperty(String s, Object o) {

      }

      @Override
      public Map<String, Object> getProperties() {
        return null;
      }

      @Override
      public void evict(final Object o) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public Object load(final Class aClass, final Serializable serializable, final LockMode lockMode) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public Object load(final Class aClass, final Serializable serializable, final LockOptions lockOptions) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public Object load(final String s, final Serializable serializable, final LockMode lockMode) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public Object load(final String s, final Serializable serializable, final LockOptions lockOptions) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public Object load(final Class aClass, final Serializable serializable) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public Object load(final String s, final Serializable serializable) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public void load(final Object o, final Serializable serializable) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public void replicate(final Object o, final ReplicationMode replicationMode) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public void replicate(final String s, final Object o, final ReplicationMode replicationMode) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public Serializable save(final Object o) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public Serializable save(final String s, final Object o) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public void saveOrUpdate(final Object o) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public void saveOrUpdate(final String s, final Object o) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public void update(final Object o) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public void update(final String s, final Object o) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public Object merge(final Object o) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public Object merge(final String s, final Object o) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public void persist(final Object o) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public void remove(Object o) {

      }

      @Override
      public <T> T find(Class<T> aClass, Object o) {
        return null;
      }

      @Override
      public <T> T find(Class<T> aClass, Object o, Map<String, Object> map) {
        return null;
      }

      @Override
      public <T> T find(Class<T> aClass, Object o, LockModeType lockModeType) {
        return null;
      }

      @Override
      public <T> T find(Class<T> aClass, Object o, LockModeType lockModeType, Map<String, Object> map) {
        return null;
      }

      @Override
      public <T> T getReference(Class<T> aClass, Object o) {
        return null;
      }

      @Override
      public void persist(final String s, final Object o) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public void delete(final Object o) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public void delete(final String s, final Object o) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public void lock(final Object o, final LockMode lockMode) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public void lock(final String s, final Object o, final LockMode lockMode) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public LockRequest buildLockRequest(final LockOptions lockOptions) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public void refresh(final Object o) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public void refresh(Object o, Map<String, Object> map) {

      }

      @Override
      public void refresh(Object o, LockModeType lockModeType) {

      }

      @Override
      public void refresh(Object o, LockModeType lockModeType, Map<String, Object> map) {

      }

      @Override
      public void refresh(final String s, final Object o) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public void refresh(final Object o, final LockMode lockMode) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public void refresh(final Object o, final LockOptions lockOptions) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public void refresh(final String s, final Object o, final LockOptions lockOptions) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public LockMode getCurrentLockMode(final Object o) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public org.hibernate.query.Query createFilter(Object collection, String queryString) {
        return null;
      }

      @Override
      public void clear() {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public void detach(Object o) {

      }

      @Override
      public Object get(final Class aClass, final Serializable serializable) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public Object get(final Class aClass, final Serializable serializable, final LockMode lockMode) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public Object get(final Class aClass, final Serializable serializable, final LockOptions lockOptions) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public Object get(final String s, final Serializable serializable) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public Object get(final String s, final Serializable serializable, final LockMode lockMode) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public Object get(final String s, final Serializable serializable, final LockOptions lockOptions) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public String getEntityName(final Object o) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public IdentifierLoadAccess byId(final String s) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public <T> MultiIdentifierLoadAccess<T> byMultipleIds(Class<T> entityClass) {
        return null;
      }

      @Override
      public MultiIdentifierLoadAccess byMultipleIds(String entityName) {
        return null;
      }

      @Override
      public IdentifierLoadAccess byId(final Class aClass) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public NaturalIdLoadAccess byNaturalId(final String s) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public NaturalIdLoadAccess byNaturalId(final Class aClass) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public SimpleNaturalIdLoadAccess bySimpleNaturalId(final String s) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public SimpleNaturalIdLoadAccess bySimpleNaturalId(final Class aClass) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public Filter enableFilter(final String s) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public Filter getEnabledFilter(final String s) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public void disableFilter(final String s) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public SessionStatistics getStatistics() {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public boolean isReadOnly(final Object o) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public void setReadOnly(final Object o, final boolean b) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public void doWork(final Work work) throws HibernateException {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public <T> T doReturningWork(final ReturningWork<T> tReturningWork) throws HibernateException {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public Connection disconnect() {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public void reconnect(final Connection connection) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public boolean isFetchProfileEnabled(final String s) throws UnknownProfileException {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public void enableFetchProfile(final String s) throws UnknownProfileException {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public void disableFetchProfile(final String s) throws UnknownProfileException {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public TypeHelper getTypeHelper() {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public LobHelper getLobHelper() {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public void addEventListeners(final SessionEventListener... sessionEventListeners) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public org.hibernate.query.Query getNamedQuery(String queryName) {
        return null;
      }

      @Override
      public org.hibernate.query.Query createQuery(String queryString) {
        return null;
      }

      @Override
      public String getTenantIdentifier() {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public Transaction beginTransaction() {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public Transaction getTransaction() {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public ProcedureCall getNamedProcedureCall(String name) {
        return null;
      }

      @Override
      public ProcedureCall createStoredProcedureCall(String procedureName) {
        return null;
      }

      @Override
      public ProcedureCall createStoredProcedureCall(String procedureName, Class... resultClasses) {
        return null;
      }

      @Override
      public ProcedureCall createStoredProcedureCall(String procedureName, String... resultSetMappings) {
        return null;
      }

      @Override
      public EntityManagerFactory getEntityManagerFactory() {
        return null;
      }

      @Override
      public CriteriaBuilder getCriteriaBuilder() {
        return null;
      }

      @Override
      public Metamodel getMetamodel() {
        return null;
      }

      @Override
      public <T> EntityGraph<T> createEntityGraph(Class<T> aClass) {
        return null;
      }

      @Override
      public EntityGraph<?> createEntityGraph(String s) {
        return null;
      }

      @Override
      public EntityGraph<?> getEntityGraph(String s) {
        return null;
      }

      @Override
      public <T> List<EntityGraph<? super T>> getEntityGraphs(Class<T> aClass) {
        return null;
      }

      @Override
      public <T> org.hibernate.query.Query<T> createQuery(String queryString, Class<T> resultType) {
        return null;
      }

      @Override
      public org.hibernate.query.Query createNamedQuery(String name) {
        return null;
      }

      @Override
      public <T> org.hibernate.query.Query<T> createQuery(CriteriaQuery<T> criteriaQuery) {
        return null;
      }

      @Override
      public org.hibernate.query.Query createQuery(CriteriaUpdate updateQuery) {
        return null;
      }

      @Override
      public org.hibernate.query.Query createQuery(CriteriaDelete deleteQuery) {
        return null;
      }

      @Override
      public <T> org.hibernate.query.Query<T> createNamedQuery(String name, Class<T> resultType) {
        return null;
      }

      @Override
      public NativeQuery createNativeQuery(String s, Class aClass) {
        return null;
      }

      @Override
      public StoredProcedureQuery createNamedStoredProcedureQuery(String s) {
        return null;
      }

      @Override
      public StoredProcedureQuery createStoredProcedureQuery(String s) {
        return null;
      }

      @Override
      public StoredProcedureQuery createStoredProcedureQuery(String s, Class... classes) {
        return null;
      }

      @Override
      public StoredProcedureQuery createStoredProcedureQuery(String s, String... strings) {
        return null;
      }

      @Override
      public void joinTransaction() {

      }

      @Override
      public boolean isJoinedToTransaction() {
        return false;
      }

      @Override
      public <T> T unwrap(Class<T> aClass) {
        return null;
      }

      @Override
      public Object getDelegate() {
        return null;
      }

      @Override
      public NativeQuery createSQLQuery(final String s) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public NativeQuery createNativeQuery(String sqlString) {
        return null;
      }

      @Override
      public NativeQuery createNativeQuery(String sqlString, String resultSetMapping) {
        return null;
      }

      @Override
      public NativeQuery getNamedNativeQuery(String name) {
        return null;
      }

      @Override
      public Criteria createCriteria(final Class aClass) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public Criteria createCriteria(final Class aClass, final String s) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public Criteria createCriteria(final String s) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public Criteria createCriteria(final String s, final String s2) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public Integer getJdbcBatchSize() {
        return null;
      }

      @Override
      public void setJdbcBatchSize(Integer jdbcBatchSize) {

      }
    };
    int value;
  }
  @SuppressWarnings({"unused", "rawtypes"})
  public static class SessionImplementorObject {
    SessionImplementor sessionImplementor = new SessionImplementor() {
      @Override
      public boolean useStreamForLobBinding() {
        return false;
      }

      @Override
      public LobCreator getLobCreator() {
        return null;
      }

      @Override
      public SqlTypeDescriptor remapSqlTypeDescriptor(SqlTypeDescriptor sqlTypeDescriptor) {
        return null;
      }

      @Override
      public TimeZone getJdbcTimeZone() {
        return null;
      }

      @Override
      public boolean shouldAutoJoinTransaction() {
        return false;
      }

      @Override
      public String getTenantIdentifier() {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public void close() {

      }

      @Override
      public UUID getSessionIdentifier() {
        return null;
      }

      @Override
      public JdbcSessionContext getJdbcSessionContext() {
        return null;
      }

      @Override
      public JdbcConnectionAccess getJdbcConnectionAccess() {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public EntityKey generateEntityKey(final Serializable serializable, final EntityPersister entityPersister) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public Interceptor getInterceptor() {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public void setAutoClear(final boolean b) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public SessionImplementor getSession() {
        return null;
      }

      @Override
      public boolean isTransactionInProgress() {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public Transaction accessTransaction() {
        return null;
      }

      @Override
      public LockOptions getLockRequest(LockModeType lockModeType, Map<String, Object> properties) {
        return null;
      }

      @Override
      public void initializeCollection(final PersistentCollection persistentCollection, final boolean b) throws HibernateException {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public Object internalLoad(final String s, final Serializable serializable, final boolean b, final boolean b2) throws HibernateException {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public Object immediateLoad(final String s, final Serializable serializable) throws HibernateException {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public long getTimestamp() {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public SessionFactoryImplementor getFactory() {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public List list(final String s, final QueryParameters queryParameters) throws HibernateException {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public Iterator iterate(final String s, final QueryParameters queryParameters) throws HibernateException {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public ScrollableResultsImplementor scroll(String query, QueryParameters queryParameters) throws HibernateException {
        return null;
      }

      @Override
      public ScrollableResultsImplementor scroll(Criteria criteria, ScrollMode scrollMode) {
        return null;
      }

      @Override
      public List list(final Criteria criteria) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public List listFilter(final Object o, final String s, final QueryParameters queryParameters) throws HibernateException {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public Iterator iterateFilter(final Object o, final String s, final QueryParameters queryParameters) throws HibernateException {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public EntityPersister getEntityPersister(final String s, final Object o) throws HibernateException {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public Object getEntityUsingInterceptor(final EntityKey entityKey) throws HibernateException {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public Serializable getContextEntityIdentifier(final Object o) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public String bestGuessEntityName(final Object o) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public String guessEntityName(final Object o) throws HibernateException {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public Object instantiate(final String s, final Serializable serializable) throws HibernateException {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public List listCustomQuery(final CustomQuery customQuery, final QueryParameters queryParameters) throws HibernateException {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public ScrollableResultsImplementor scrollCustomQuery(CustomQuery customQuery, QueryParameters queryParameters) throws HibernateException {
        return null;
      }

      @Override
      public List list(final NativeSQLQuerySpecification nativeSQLQuerySpecification, final QueryParameters queryParameters) throws HibernateException {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public ScrollableResultsImplementor scroll(NativeSQLQuerySpecification spec, QueryParameters queryParameters) {
        return null;
      }

      @Override
      public int getDontFlushFromFind() {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public PersistenceContext getPersistenceContext() {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public JdbcCoordinator getJdbcCoordinator() {
        return null;
      }

      @Override
      public JdbcServices getJdbcServices() {
        return null;
      }

      @Override
      public int executeUpdate(final String s, final QueryParameters queryParameters) throws HibernateException {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public int executeNativeUpdate(final NativeSQLQuerySpecification nativeSQLQuerySpecification, final QueryParameters queryParameters) throws HibernateException {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public CacheMode getCacheMode() {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public void setCacheMode(final CacheMode cacheMode) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public boolean isOpen() {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public EntityManagerFactory getEntityManagerFactory() {
        return null;
      }

      @Override
      public CriteriaBuilder getCriteriaBuilder() {
        return null;
      }

      @Override
      public Metamodel getMetamodel() {
        return null;
      }

      @Override
      public <T> EntityGraph<T> createEntityGraph(Class<T> aClass) {
        return null;
      }

      @Override
      public EntityGraph<?> createEntityGraph(String s) {
        return null;
      }

      @Override
      public EntityGraph<?> getEntityGraph(String s) {
        return null;
      }

      @Override
      public <T> List<EntityGraph<? super T>> getEntityGraphs(Class<T> aClass) {
        return null;
      }

      @Override
      public boolean isConnected() {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public Transaction beginTransaction() {
        return null;
      }

      @Override
      public Transaction getTransaction() {
        return null;
      }

      @Override
      public ProcedureCall getNamedProcedureCall(String name) {
        return null;
      }

      @Override
      public ProcedureCall createStoredProcedureCall(String procedureName) {
        return null;
      }

      @Override
      public ProcedureCall createStoredProcedureCall(String procedureName, Class... resultClasses) {
        return null;
      }

      @Override
      public ProcedureCall createStoredProcedureCall(String procedureName, String... resultSetMappings) {
        return null;
      }

      @Override
      public Criteria createCriteria(Class persistentClass) {
        return null;
      }

      @Override
      public Criteria createCriteria(Class persistentClass, String alias) {
        return null;
      }

      @Override
      public Criteria createCriteria(String entityName) {
        return null;
      }

      @Override
      public Criteria createCriteria(String entityName, String alias) {
        return null;
      }

      @Override
      public Integer getJdbcBatchSize() {
        return null;
      }

      @Override
      public void setJdbcBatchSize(Integer jdbcBatchSize) {
      }

      @Override
      public void lock(Object o, LockModeType lockModeType) {
      }

      @Override
      public void lock(Object o, LockModeType lockModeType, Map<String, Object> map) {
      }

      @Override
      public void setHibernateFlushMode(FlushMode flushMode) {
      }

      @Override
      public FlushMode getHibernateFlushMode() {
        return null;
      }

      @Override
      public void setFlushMode(final FlushMode flushMode) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public FlushModeType getFlushMode() {
        return null;
      }

      @Override
      public Connection connection() {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public SharedSessionBuilder sessionWithOptions() {
        return null;
      }

      @Override
      public void flush() {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public void setFlushMode(FlushModeType flushModeType) {

      }

      @Override
      public SessionFactoryImplementor getSessionFactory() {
        return null;
      }

      @Override
      public void cancelQuery() throws HibernateException {

      }

      @Override
      public boolean isDirty() throws HibernateException {
        return false;
      }

      @Override
      public boolean isDefaultReadOnly() {
        return false;
      }

      @Override
      public void setDefaultReadOnly(boolean readOnly) {

      }

      @Override
      public Serializable getIdentifier(Object object) {
        return null;
      }

      @Override
      public boolean contains(String entityName, Object object) {
        return false;
      }

      @Override
      public void evict(Object object) {

      }

      @Override
      public <T> T load(Class<T> theClass, Serializable id, LockMode lockMode) {
        return null;
      }

      @Override
      public <T> T load(Class<T> theClass, Serializable id, LockOptions lockOptions) {
        return null;
      }

      @Override
      public Object load(String entityName, Serializable id, LockMode lockMode) {
        return null;
      }

      @Override
      public Object load(String entityName, Serializable id, LockOptions lockOptions) {
        return null;
      }

      @Override
      public <T> T load(Class<T> theClass, Serializable id) {
        return null;
      }

      @Override
      public Object load(String entityName, Serializable id) {
        return null;
      }

      @Override
      public void load(Object object, Serializable id) {

      }

      @Override
      public void replicate(Object object, ReplicationMode replicationMode) {

      }

      @Override
      public void replicate(String entityName, Object object, ReplicationMode replicationMode) {

      }

      @Override
      public Serializable save(Object object) {
        return null;
      }

      @Override
      public Serializable save(String entityName, Object object) {
        return null;
      }

      @Override
      public void saveOrUpdate(Object object) {

      }

      @Override
      public void saveOrUpdate(String entityName, Object object) {

      }

      @Override
      public void update(Object object) {

      }

      @Override
      public void update(String entityName, Object object) {

      }

      @Override
      public Object merge(Object object) {
        return null;
      }

      @Override
      public Object merge(String entityName, Object object) {
        return null;
      }

      @Override
      public void persist(Object object) {

      }

      @Override
      public void remove(Object o) {

      }

      @Override
      public <T> T find(Class<T> aClass, Object o) {
        return null;
      }

      @Override
      public <T> T find(Class<T> aClass, Object o, Map<String, Object> map) {
        return null;
      }

      @Override
      public <T> T find(Class<T> aClass, Object o, LockModeType lockModeType) {
        return null;
      }

      @Override
      public <T> T find(Class<T> aClass, Object o, LockModeType lockModeType, Map<String, Object> map) {
        return null;
      }

      @Override
      public <T> T getReference(Class<T> aClass, Object o) {
        return null;
      }

      @Override
      public void persist(String entityName, Object object) {

      }

      @Override
      public void delete(Object object) {

      }

      @Override
      public void delete(String entityName, Object object) {

      }

      @Override
      public void lock(Object object, LockMode lockMode) {

      }

      @Override
      public void lock(String entityName, Object object, LockMode lockMode) {

      }

      @Override
      public LockRequest buildLockRequest(LockOptions lockOptions) {
        return null;
      }

      @Override
      public void refresh(Object object) {

      }

      @Override
      public void refresh(Object o, Map<String, Object> map) {

      }

      @Override
      public void refresh(Object o, LockModeType lockModeType) {

      }

      @Override
      public void refresh(Object o, LockModeType lockModeType, Map<String, Object> map) {

      }

      @Override
      public void refresh(String entityName, Object object) {

      }

      @Override
      public void refresh(Object object, LockMode lockMode) {

      }

      @Override
      public void refresh(Object object, LockOptions lockOptions) {

      }

      @Override
      public void refresh(String entityName, Object object, LockOptions lockOptions) {

      }

      @Override
      public LockMode getCurrentLockMode(Object object) {
        return null;
      }

      @Override
      public org.hibernate.query.Query createFilter(Object collection, String queryString) {
        return null;
      }

      @Override
      public void clear() {

      }

      @Override
      public void detach(Object o) {

      }

      @Override
      public boolean contains(Object o) {
        return false;
      }

      @Override
      public LockModeType getLockMode(Object o) {
        return null;
      }

      @Override
      public void setProperty(String s, Object o) {

      }

      @Override
      public Map<String, Object> getProperties() {
        return null;
      }

      @Override
      public <T> T get(Class<T> entityType, Serializable id) {
        return null;
      }

      @Override
      public <T> T get(Class<T> entityType, Serializable id, LockMode lockMode) {
        return null;
      }

      @Override
      public <T> T get(Class<T> entityType, Serializable id, LockOptions lockOptions) {
        return null;
      }

      @Override
      public Object get(String entityName, Serializable id) {
        return null;
      }

      @Override
      public Object get(String entityName, Serializable id, LockMode lockMode) {
        return null;
      }

      @Override
      public Object get(String entityName, Serializable id, LockOptions lockOptions) {
        return null;
      }

      @Override
      public String getEntityName(Object object) {
        return null;
      }

      @Override
      public IdentifierLoadAccess byId(String entityName) {
        return null;
      }

      @Override
      public <T> MultiIdentifierLoadAccess<T> byMultipleIds(Class<T> entityClass) {
        return null;
      }

      @Override
      public MultiIdentifierLoadAccess byMultipleIds(String entityName) {
        return null;
      }

      @Override
      public <T> IdentifierLoadAccess<T> byId(Class<T> entityClass) {
        return null;
      }

      @Override
      public NaturalIdLoadAccess byNaturalId(String entityName) {
        return null;
      }

      @Override
      public <T> NaturalIdLoadAccess<T> byNaturalId(Class<T> entityClass) {
        return null;
      }

      @Override
      public SimpleNaturalIdLoadAccess bySimpleNaturalId(String entityName) {
        return null;
      }

      @Override
      public <T> SimpleNaturalIdLoadAccess<T> bySimpleNaturalId(Class<T> entityClass) {
        return null;
      }

      @Override
      public Filter enableFilter(String filterName) {
        return null;
      }

      @Override
      public Filter getEnabledFilter(String filterName) {
        return null;
      }

      @Override
      public void disableFilter(String filterName) {

      }

      @Override
      public SessionStatistics getStatistics() {
        return null;
      }

      @Override
      public boolean isReadOnly(Object entityOrProxy) {
        return false;
      }

      @Override
      public void setReadOnly(Object entityOrProxy, boolean readOnly) {

      }

      @Override
      public void doWork(Work work) throws HibernateException {

      }

      @Override
      public <T> T doReturningWork(ReturningWork<T> work) throws HibernateException {
        return null;
      }

      @Override
      public Connection disconnect() {
        return null;
      }

      @Override
      public void reconnect(Connection connection) {

      }

      @Override
      public boolean isFetchProfileEnabled(String name) throws UnknownProfileException {
        return false;
      }

      @Override
      public void enableFetchProfile(String name) throws UnknownProfileException {

      }

      @Override
      public void disableFetchProfile(String name) throws UnknownProfileException {

      }

      @Override
      public TypeHelper getTypeHelper() {
        return null;
      }

      @Override
      public LobHelper getLobHelper() {
        return null;
      }

      @Override
      public void addEventListeners(SessionEventListener... listeners) {

      }

      @Override
      public boolean isFlushBeforeCompletionEnabled() {
        return false;
      }

      @Override
      public ActionQueue getActionQueue() {
        return null;
      }

      @Override
      public Object instantiate(EntityPersister persister, Serializable id) throws HibernateException {
        return null;
      }

      @Override
      public void forceFlush(EntityEntry e) throws HibernateException {

      }

      @Override
      public QueryImplementor createQuery(String queryString) {
        return null;
      }

      @Override
      public <T> QueryImplementor<T> createQuery(String queryString, Class<T> resultType) {
        return null;
      }

      @Override
      public <T> QueryImplementor<T> createNamedQuery(String name, Class<T> resultType) {
        return null;
      }

      @Override
      public QueryImplementor createNamedQuery(String name) {
        return null;
      }

      @Override
      public NativeQueryImplementor createNativeQuery(String sqlString) {
        return null;
      }

      @Override
      public NativeQueryImplementor createNativeQuery(String sqlString, Class resultClass) {
        return null;
      }

      @Override
      public NativeQueryImplementor createNativeQuery(String sqlString, String resultSetMapping) {
        return null;
      }

      @Override
      public StoredProcedureQuery createNamedStoredProcedureQuery(String s) {
        return null;
      }

      @Override
      public StoredProcedureQuery createStoredProcedureQuery(String s) {
        return null;
      }

      @Override
      public StoredProcedureQuery createStoredProcedureQuery(String s, Class... classes) {
        return null;
      }

      @Override
      public StoredProcedureQuery createStoredProcedureQuery(String s, String... strings) {
        return null;
      }

      @Override
      public void joinTransaction() {

      }

      @Override
      public boolean isJoinedToTransaction() {
        return false;
      }

      @Override
      public <T> T unwrap(Class<T> aClass) {
        return null;
      }

      @Override
      public Object getDelegate() {
        return null;
      }

      @Override
      public NativeQueryImplementor getNamedNativeQuery(String name) {
        return null;
      }

      @Override
      public QueryImplementor getNamedQuery(final String s) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public NativeQueryImplementor getNamedSQLQuery(final String s) {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public <T> QueryImplementor<T> createQuery(CriteriaQuery<T> criteriaQuery) {
        return null;
      }

      @Override
      public QueryImplementor createQuery(CriteriaUpdate updateQuery) {
        return null;
      }

      @Override
      public QueryImplementor createQuery(CriteriaDelete deleteQuery) {
        return null;
      }

      @Override
      public <T> QueryImplementor<T> createQuery(String jpaqlString, Class<T> resultClass, Selection selection, QueryOptions queryOptions) {
        return null;
      }

      @Override
      public void merge(String entityName, Object object, Map copiedAlready) throws HibernateException {

      }

      @Override
      public void persist(String entityName, Object object, Map createdAlready) throws HibernateException {

      }

      @Override
      public void persistOnFlush(String entityName, Object object, Map copiedAlready) {

      }

      @Override
      public void refresh(String entityName, Object object, Map refreshedAlready) throws HibernateException {

      }

      @Override
      public void delete(String entityName, Object child, boolean isCascadeDeleteEnabled, Set transientEntities) {

      }

      @Override
      public void removeOrphanBeforeUpdates(String entityName, Object child) {

      }

      @Override
      public boolean isEventSource() {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public void afterScrollOperation() {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public boolean shouldAutoClose() {
        return false;
      }

      @Override
      public boolean isAutoCloseSessionEnabled() {
        return false;
      }

      @Override
      public TransactionCoordinator getTransactionCoordinator() {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public void afterTransactionBegin() {

      }

      @Override
      public void beforeTransactionCompletion() {

      }

      @Override
      public void afterTransactionCompletion(boolean successful, boolean delayed) {

      }

      @Override
      public void flushBeforeTransactionCompletion() {

      }

      @Override
      public boolean isClosed() {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public void checkOpen(boolean markForRollbackIfClosed) {

      }

      @Override
      public void markForRollbackOnly() {

      }

      @Override
      public LoadQueryInfluencers getLoadQueryInfluencers() {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public ExceptionConverter getExceptionConverter() {
        return null;
      }

      @Override
      public SessionEventListenerManager getEventListenerManager() {
        throw new UnsupportedOperationException("Implement me!");
      }

      @Override
      public <T> T execute(final Callback<T> tCallback) {
        throw new UnsupportedOperationException("Implement me!");
      }
    };
    int value;
  }
}
