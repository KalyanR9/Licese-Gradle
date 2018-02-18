import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;

import org.gradle.api.Action;
import org.gradle.api.DomainObjectCollection;
import org.gradle.api.NamedDomainObjectList;
import org.gradle.api.Namer;
import org.gradle.api.Rule;
import org.gradle.api.artifacts.ArtifactRepositoryContainer;
import org.gradle.api.artifacts.UnknownRepositoryException;
import org.gradle.api.artifacts.dsl.RepositoryHandler;
import org.gradle.api.artifacts.repositories.ArtifactRepository;
import org.gradle.api.artifacts.repositories.FlatDirectoryArtifactRepository;
import org.gradle.api.artifacts.repositories.IvyArtifactRepository;
import org.gradle.api.artifacts.repositories.MavenArtifactRepository;
import org.gradle.api.specs.Spec;

import groovy.lang.Closure;

public class Test implements RepositoryHandler{

	@Override
	public boolean add(ArtifactRepository arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addFirst(ArtifactRepository arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addLast(ArtifactRepository arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArtifactRepository getAt(String arg0) throws UnknownRepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArtifactRepository getByName(String arg0) throws UnknownRepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArtifactRepository getByName(String arg0, Closure arg1) throws UnknownRepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ArtifactRepository> findAll(Closure arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NamedDomainObjectList<ArtifactRepository> matching(Spec<? super ArtifactRepository> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NamedDomainObjectList<ArtifactRepository> matching(Closure arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends ArtifactRepository> NamedDomainObjectList<S> withType(Class<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addAll(Collection<? extends ArtifactRepository> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Rule addRule(Rule arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rule addRule(String arg0, Closure arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArtifactRepository findByName(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SortedMap<String, ArtifactRepository> getAsMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Namer<ArtifactRepository> getNamer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SortedSet<String> getNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Rule> getRules() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void all(Action<? super ArtifactRepository> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void all(Closure arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Action<? super ArtifactRepository> whenObjectAdded(Action<? super ArtifactRepository> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void whenObjectAdded(Closure arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Action<? super ArtifactRepository> whenObjectRemoved(Action<? super ArtifactRepository> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void whenObjectRemoved(Closure arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <S extends ArtifactRepository> DomainObjectCollection<S> withType(Class<S> arg0, Action<? super S> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends ArtifactRepository> DomainObjectCollection<S> withType(Class<S> arg0, Closure arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean contains(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator<ArtifactRepository> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] toArray(T[] arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(int arg0, ArtifactRepository arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean addAll(int arg0, Collection<? extends ArtifactRepository> arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArtifactRepository get(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int indexOf(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int lastIndexOf(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ListIterator<ArtifactRepository> listIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListIterator<ArtifactRepository> listIterator(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArtifactRepository remove(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArtifactRepository set(int arg0, ArtifactRepository arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ArtifactRepository> subList(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArtifactRepositoryContainer configure(Closure arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FlatDirectoryArtifactRepository flatDir(Map<String, ?> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FlatDirectoryArtifactRepository flatDir(Closure arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FlatDirectoryArtifactRepository flatDir(Action<? super FlatDirectoryArtifactRepository> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IvyArtifactRepository ivy(Closure arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IvyArtifactRepository ivy(Action<? super IvyArtifactRepository> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MavenArtifactRepository jcenter() {
		// TODO Auto-generated method stub
		return jcenter();
	}

	@Override
	public MavenArtifactRepository jcenter(Action<? super MavenArtifactRepository> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MavenArtifactRepository maven(Closure arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MavenArtifactRepository maven(Action<? super MavenArtifactRepository> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MavenArtifactRepository mavenCentral() {
		// TODO Auto-generated method stub
		return mavenCentral();
	}

	@Override
	public MavenArtifactRepository mavenCentral(Map<String, ?> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MavenArtifactRepository mavenLocal() {
		// TODO Auto-generated method stub
		return mavenLocal();
	}

}
