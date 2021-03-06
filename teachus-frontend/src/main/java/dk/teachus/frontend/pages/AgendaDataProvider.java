package dk.teachus.frontend.pages;

import org.apache.wicket.model.IModel;

import dk.teachus.backend.domain.PupilBooking;
import dk.teachus.frontend.components.list.DateComparator;
import dk.teachus.frontend.components.list.DoubleComparator;
import dk.teachus.frontend.components.list.StringComparator;
import dk.teachus.frontend.components.list.TeachUsSortableDataProvider;

public class AgendaDataProvider extends TeachUsSortableDataProvider<PupilBooking> {
	private static final long serialVersionUID = 1L;

	public AgendaDataProvider(IModel listModel) {
		super(listModel);
		
		addComparator("pupil.name", new StringComparator());
		addComparator("date.date", new DateComparator());
		addComparator("pupil.phoneNumber", new StringComparator());
		addComparator("period.price", new DoubleComparator());
		
		setSort("date.date", true);
	}
	
}
