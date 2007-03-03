package dk.teachus.frontend.pages;

import java.util.List;

import wicket.RestartResponseAtInterceptPageException;
import wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import wicket.extensions.markup.html.repeater.data.table.IColumn;
import wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import wicket.markup.repeater.Item;
import wicket.model.IModel;
import wicket.model.Model;
import wicket.protocol.http.WebApplication;
import dk.teachus.dao.BookingDAO;
import dk.teachus.domain.Pupil;
import dk.teachus.domain.PupilBooking;
import dk.teachus.domain.Teacher;
import dk.teachus.frontend.TeachUsApplication;
import dk.teachus.frontend.TeachUsSession;
import dk.teachus.frontend.UserLevel;
import dk.teachus.frontend.components.ListPanel;
import dk.teachus.frontend.components.PaidPanel;
import dk.teachus.frontend.components.RendererPropertyColumn;
import dk.teachus.frontend.utils.CurrencyChoiceRenderer;
import dk.teachus.frontend.utils.DateChoiceRenderer;
import dk.teachus.frontend.utils.TimeChoiceRenderer;
import dk.teachus.frontend.utils.YesNoRenderer;

public class PaymentPage extends AuthenticatedBasePage {
	private static final long serialVersionUID = 1L;

	public PaymentPage() {
		super(UserLevel.PUPIL);
		
		BookingDAO bookingDAO = TeachUsApplication.get().getBookingDAO();
		List<PupilBooking> pupilBookings = null;
		if (TeachUsSession.get().getPerson() instanceof Pupil) {
			pupilBookings = bookingDAO.getUnpaidBookings((Pupil) TeachUsSession.get().getPerson());
		} else if (TeachUsSession.get().getPerson() instanceof Teacher) {
			pupilBookings = bookingDAO.getUnpaidBookings((Teacher) TeachUsSession.get().getPerson());
		} else {
			throw new RestartResponseAtInterceptPageException(WebApplication.get().getHomePage());
		}
		
		init(pupilBookings);
	}
	
	private void init(final List<PupilBooking> pupilBookings) {
		IColumn paidColumn;
		Model paidHeader = new Model(TeachUsSession.get().getString("General.paid")); //$NON-NLS-1$
		if (TeachUsSession.get().getUserLevel() == UserLevel.TEACHER) {
			paidColumn = new AbstractColumn(paidHeader) {
				private static final long serialVersionUID = 1L;

				public void populateItem(Item cellItem, String componentId, IModel rowModel) {
					cellItem.add(new PaidPanel(componentId, rowModel));
				}
			};
		} else {
			paidColumn = new RendererPropertyColumn(paidHeader, "paid", new YesNoRenderer()); //$NON-NLS-1$ //$NON-NLS-2$
		}
		
		IColumn[] columns = new IColumn[] {
				new PropertyColumn(new Model(TeachUsSession.get().getString("General.pupil")), "pupil.name"), //$NON-NLS-1$ //$NON-NLS-2$
				new RendererPropertyColumn(new Model(TeachUsSession.get().getString("General.date")), "date", new DateChoiceRenderer()), //$NON-NLS-1$ //$NON-NLS-2$
				new RendererPropertyColumn(new Model(TeachUsSession.get().getString("General.time")), "date", new TimeChoiceRenderer()), //$NON-NLS-1$ //$NON-NLS-2$
				new RendererPropertyColumn(new Model(TeachUsSession.get().getString("General.price")), "period.price", new CurrencyChoiceRenderer()), //$NON-NLS-1$ //$NON-NLS-2$
				paidColumn
		};

		add(new ListPanel("list", columns, pupilBookings));
	}

	@Override
	protected String getPageLabel() {
		return TeachUsSession.get().getString("General.payment"); //$NON-NLS-1$
	}

	@Override
	protected PageCategory getPageCategory() {
		return PageCategory.PAYMENT;
	}

}